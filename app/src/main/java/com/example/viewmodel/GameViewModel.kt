package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.GameRepository
import com.example.model.AchievementItem
import com.example.model.AchievementsCatalog
import com.example.model.CharacterSkinCatalog
import com.example.model.EvolutionStageInfo
import com.example.model.EvolutionStages
import com.example.model.GameStats
import com.example.model.JawUpgradeDef
import com.example.model.JawUpgradeItem
import com.example.model.JawUpgradesCatalog
import com.example.model.KochUpgradeDef
import com.example.model.KochUpgradeItem
import com.example.model.KochUpgradesCatalog
import com.example.model.PassiveUpgradeDef
import com.example.model.PassiveUpgradeItem
import com.example.model.PassiveUpgradesCatalog
import com.example.model.SkinDef
import com.example.model.SkinItem
import com.example.util.FormatUtil
import com.example.util.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

data class ClickParticle(
    val id: Long,
    val text: String,
    val x: Float,
    val y: Float,
    val colorHex: Long = 0xFF00E5FF,
    val timestamp: Long = System.currentTimeMillis()
)

data class GameUiState(
    val auraPoints: Double = 0.0,
    val totalAuraEarned: Double = 0.0,
    val totalMogs: Long = 0L,
    val bonesmashHits: Long = 0L,
    val evolutionStage: Int = 0,
    val currentEvolution: EvolutionStageInfo = EvolutionStages.getStage(0),
    val nextEvolution: EvolutionStageInfo? = EvolutionStages.getStage(1),
    val canEvolve: Boolean = false,
    val clickPower: Double = 1.0,
    val auraPerSec: Double = 0.0,
    val jawUpgrades: List<JawUpgradeItem> = emptyList(),
    val passiveUpgrades: List<PassiveUpgradeItem> = emptyList(),
    val kochUpgrades: List<KochUpgradeItem> = emptyList(),
    val isKochModeActive: Boolean = false,
    val kochModeRemainingSec: Int = 0,
    val kochAdrenaline: Float = 0f,
    val currentCombo: Int = 0,
    val comboMultiplier: Double = 1.0,
    val offlineAuraEarned: Double? = null,
    val particles: List<ClickParticle> = emptyList(),
    val hapticsEnabled: Boolean = true,
    val showEvolutionCelebration: Boolean = false,
    val justEvolvedStage: EvolutionStageInfo? = null,
    val bonesmashProgress: Float = 0f, // 0f..1f for jaw remodeling bar
    val soundEnabled: Boolean = true,
    val achievements: List<AchievementItem> = emptyList(),
    val claimedAchievementIds: Set<String> = emptySet(),
    val activeSkin: SkinDef = CharacterSkinCatalog.SKINS.first(),
    val skins: List<SkinItem> = emptyList()
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    private val vibrator: Vibrator?

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var rawStats: GameStats = GameStats()
    private var comboResetJob: Job? = null
    private var particleCounter = 0L
    private var kochTimerJob: Job? = null
    private var claimedAchievementIds = mutableSetOf<String>()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = GameRepository(database.gameDao())

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        // Initialize state from database
        viewModelScope.launch {
            val savedStats = repository.getStatsDirect()
            rawStats = savedStats
            
            // Check offline earnings
            val now = System.currentTimeMillis()
            val elapsedSec = ((now - savedStats.lastTimestamp) / 1000).coerceAtLeast(0)
            
            recalculateState()

            if (elapsedSec > 15 && _uiState.value.auraPerSec > 0) {
                val earned = (elapsedSec * _uiState.value.auraPerSec * 0.75).coerceAtMost(50000000.0)
                if (earned > 5) {
                    val updatedAura = rawStats.auraPoints + earned
                    val updatedTotal = rawStats.totalAuraEarned + earned
                    rawStats = rawStats.copy(auraPoints = updatedAura, totalAuraEarned = updatedTotal)
                    _uiState.update { it.copy(offlineAuraEarned = earned) }
                    recalculateState()
                    saveCurrentStats()
                }
            }
        }

        // Main game loop ticker for passive aura generation
        viewModelScope.launch {
            val tickIntervalMs = 100L
            while (isActive) {
                delay(tickIntervalMs)
                val auraSec = _uiState.value.auraPerSec
                if (auraSec > 0) {
                    val auraInc = auraSec * (tickIntervalMs / 1000.0)
                    rawStats = rawStats.copy(
                        auraPoints = rawStats.auraPoints + auraInc,
                        totalAuraEarned = rawStats.totalAuraEarned + auraInc
                    )
                    _uiState.update { current ->
                        val newAura = current.auraPoints + auraInc
                        val newTotal = current.totalAuraEarned + auraInc
                        val nextEvo = current.nextEvolution
                        val canEvolve = nextEvo != null && newAura >= nextEvo.reqAura
                        current.copy(
                            auraPoints = newAura,
                            totalAuraEarned = newTotal,
                            canEvolve = canEvolve,
                            jawUpgrades = current.jawUpgrades.map { it.copy(canAfford = newAura >= it.currentCost) },
                            passiveUpgrades = current.passiveUpgrades.map { it.copy(canAfford = newAura >= it.currentCost) },
                            kochUpgrades = current.kochUpgrades.map { it.copy(canAfford = newAura >= it.currentCost) }
                        )
                    }
                }

                // Clean up old particles
                val now = System.currentTimeMillis()
                if (_uiState.value.particles.isNotEmpty()) {
                    _uiState.update { current ->
                        current.copy(particles = current.particles.filter { now - it.timestamp < 1200 })
                    }
                }
            }
        }

        // Periodic auto-save loop
        viewModelScope.launch {
            while (isActive) {
                delay(5000)
                saveCurrentStats()
            }
        }
    }

    private fun recalculateState() {
        val currentEvo = EvolutionStages.getStage(rawStats.evolutionStage)
        val nextEvo = if (rawStats.evolutionStage < EvolutionStages.STAGES.lastIndex) {
            EvolutionStages.getStage(rawStats.evolutionStage + 1)
        } else null

        // Jaw levels
        val jawLevels = listOf(
            rawStats.jawLevel1,
            rawStats.jawLevel2,
            rawStats.jawLevel3,
            rawStats.jawLevel4,
            rawStats.jawLevel5,
            rawStats.jawLevel6,
            rawStats.jawLevel7
        )

        var totalBaseClickPower = 1.0
        val jawItems = JawUpgradesCatalog.UPGRADES.mapIndexed { index, def ->
            val level = jawLevels.getOrElse(index) { 0 }
            val cost = JawUpgradesCatalog.calculateCost(def, level)
            val power = JawUpgradesCatalog.calculatePower(def, level)
            totalBaseClickPower += power
            JawUpgradeItem(
                def = def,
                currentLevel = level,
                currentCost = cost,
                clickPowerProvided = power,
                canAfford = rawStats.auraPoints >= cost
            )
        }

        // Passive levels
        val passiveLevels = listOf(
            rawStats.passiveLevel1,
            rawStats.passiveLevel2,
            rawStats.passiveLevel3,
            rawStats.passiveLevel4,
            rawStats.passiveLevel5,
            rawStats.passiveLevel6,
            rawStats.passiveLevel7
        )

        var totalBasePassive = 0.0
        val passiveItems = PassiveUpgradesCatalog.UPGRADES.mapIndexed { index, def ->
            val level = passiveLevels.getOrElse(index) { 0 }
            val cost = PassiveUpgradesCatalog.calculateCost(def, level)
            val power = PassiveUpgradesCatalog.calculatePower(def, level)
            totalBasePassive += power
            PassiveUpgradeItem(
                def = def,
                currentLevel = level,
                currentCost = cost,
                auraPerSecProvided = power,
                canAfford = rawStats.auraPoints >= cost
            )
        }

        // Koch levels
        val kochLevels = listOf(
            rawStats.kochLevel1,
            rawStats.kochLevel2,
            rawStats.kochLevel3,
            rawStats.kochLevel4
        )

        val kochItems = KochUpgradesCatalog.UPGRADES.mapIndexed { index, def ->
            val level = kochLevels.getOrElse(index) { 0 }
            val cost = KochUpgradesCatalog.calculateCost(def, level)
            val bonus = def.multiplierBonus * level
            KochUpgradeItem(
                def = def,
                currentLevel = level,
                currentCost = cost,
                bonusProvided = bonus,
                canAfford = rawStats.auraPoints >= cost
            )
        }

        // Active Skin & Buffs
        val activeSkin = CharacterSkinCatalog.getSkin(rawStats.selectedSkinId)
        val skinClickMult = 1.0 + activeSkin.clickMultiplierBonus
        val skinPassiveMult = 1.0 + activeSkin.passiveMultiplierBonus

        // Koch Multipliers
        val kochClickMultiplier = 1.0 + (kochItems.getOrNull(0)?.bonusProvided ?: 0.0) + (kochItems.getOrNull(2)?.bonusProvided ?: 0.0)
        val kochPassiveMultiplier = 1.0 + (kochItems.getOrNull(1)?.bonusProvided ?: 0.0)

        // Combo Multiplier
        val combo = _uiState.value.currentCombo
        val comboMult = 1.0 + (combo * 0.05).coerceAtMost(2.0)

        // Koch Rage Active Multiplier
        val kochActiveMultiplier = if (_uiState.value.isKochModeActive) 2.5 else 1.0

        val finalClickPower = totalBaseClickPower * currentEvo.clickMultiplier * skinClickMult * kochClickMultiplier * comboMult * kochActiveMultiplier
        val finalAuraSec = totalBasePassive * currentEvo.passiveMultiplier * skinPassiveMult * kochPassiveMultiplier * (if (_uiState.value.isKochModeActive) 2.0 else 1.0)

        val canEvolve = nextEvo != null && rawStats.auraPoints >= nextEvo.reqAura

        val bonesmashProgress = ((rawStats.bonesmashHits % 50).toFloat() / 50f).coerceIn(0f, 1f)

        val unlockedSkinIdsSet = rawStats.unlockedSkinIds.split(",").toSet()
        val allSkins = CharacterSkinCatalog.SKINS.map { def ->
            SkinItem(
                def = def,
                isUnlocked = unlockedSkinIdsSet.contains(def.id) || def.costAura == 0.0,
                isSelected = def.id == rawStats.selectedSkinId
            )
        }

        val achievementItems = AchievementsCatalog.ACHIEVEMENTS.map { def ->
            val unlocked = when (def.id) {
                "first_mog" -> rawStats.totalMogs >= 1
                "combo_king" -> _uiState.value.currentCombo >= 10
                "bonesmash_25" -> rawStats.bonesmashHits >= 25
                "bonesmash_100" -> rawStats.bonesmashHits >= 100
                "koch_mode" -> _uiState.value.isKochModeActive || (rawStats.kochLevel1 > 0)
                "stage_mewing" -> rawStats.evolutionStage >= 2
                "stage_chad" -> rawStats.evolutionStage >= 3
                "stage_gigachad" -> rawStats.evolutionStage >= 5
                "passive_mogger" -> finalAuraSec >= 500
                "aura_millionaire" -> rawStats.totalAuraEarned >= 1000000
                else -> false
            }
            AchievementItem(
                def = def,
                isUnlocked = unlocked,
                isClaimed = claimedAchievementIds.contains(def.id)
            )
        }

        _uiState.update { current ->
            current.copy(
                auraPoints = rawStats.auraPoints,
                totalAuraEarned = rawStats.totalAuraEarned,
                totalMogs = rawStats.totalMogs,
                bonesmashHits = rawStats.bonesmashHits,
                evolutionStage = rawStats.evolutionStage,
                currentEvolution = currentEvo,
                nextEvolution = nextEvo,
                canEvolve = canEvolve,
                clickPower = finalClickPower,
                auraPerSec = finalAuraSec,
                jawUpgrades = jawItems,
                passiveUpgrades = passiveItems,
                kochUpgrades = kochItems,
                kochAdrenaline = rawStats.kochAdrenaline,
                comboMultiplier = comboMult,
                bonesmashProgress = bonesmashProgress,
                hapticsEnabled = rawStats.hapticsEnabled,
                achievements = achievementItems,
                claimedAchievementIds = claimedAchievementIds,
                activeSkin = activeSkin,
                skins = allSkins
            )
        }
    }

    fun onMogClick(touchX: Float = 0f, touchY: Float = 0f) {
        val clickGain = _uiState.value.clickPower
        val newAura = rawStats.auraPoints + clickGain
        val newTotalAura = rawStats.totalAuraEarned + clickGain
        val newMogs = rawStats.totalMogs + 1
        val adrenalineGain = 2.0f * (1.0f + _uiState.value.activeSkin.adrenalineBonus.toFloat())
        val newAdrenaline = (rawStats.kochAdrenaline + adrenalineGain).coerceAtMost(100f)

        rawStats = rawStats.copy(
            auraPoints = newAura,
            totalAuraEarned = newTotalAura,
            totalMogs = newMogs,
            kochAdrenaline = newAdrenaline
        )

        // Combo system
        val currentCombo = _uiState.value.currentCombo + 1
        _uiState.update { it.copy(currentCombo = currentCombo) }

        comboResetJob?.cancel()
        comboResetJob = viewModelScope.launch {
            delay(1800)
            _uiState.update { it.copy(currentCombo = 0) }
            recalculateState()
        }

        // Particle generation
        val memes = listOf(
            "+${FormatUtil.formatNumber(clickGain)}",
            "MOGGED! 🤫🧏",
            "BYE BYE!",
            "HUNTER EYES!",
            "JAWLINE CHECK!",
            "AURA +1000",
            "CANTHAL TILT!",
            "CHISELED!"
        )
        val text = memes[Random.nextInt(memes.size)]
        val particle = ClickParticle(
            id = ++particleCounter,
            text = text,
            x = touchX,
            y = touchY,
            colorHex = if (_uiState.value.isKochModeActive) 0xFFFF1744 else 0xFF00E5FF
        )

        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(30)
        if (_uiState.value.soundEnabled) SoundManager.playMogPunch()
        recalculateState()
    }

    fun onBonesmashClick() {
        // Bonesmashing mechanic
        val clickGain = _uiState.value.clickPower * 1.5
        val newHits = rawStats.bonesmashHits + 1
        val newAura = rawStats.auraPoints + clickGain
        val newTotalAura = rawStats.totalAuraEarned + clickGain
        val newAdrenaline = (rawStats.kochAdrenaline + 4.0f).coerceAtMost(100f)

        rawStats = rawStats.copy(
            auraPoints = newAura,
            totalAuraEarned = newTotalAura,
            bonesmashHits = newHits,
            kochAdrenaline = newAdrenaline
        )

        vibrate(60)
        if (_uiState.value.soundEnabled) SoundManager.playBonesmashCrack()

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "БОНСМЕШИНГ! 🔨 +${FormatUtil.formatNumber(clickGain)}",
            x = 250f + Random.nextInt(-60, 60),
            y = 350f + Random.nextInt(-40, 40),
            colorHex = 0xFFFFD600
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        recalculateState()
    }

    fun buyJawUpgrade(upgradeId: Int) {
        val currentItem = _uiState.value.jawUpgrades.find { it.def.id == upgradeId } ?: return
        if (rawStats.auraPoints < currentItem.currentCost) return

        val newAura = rawStats.auraPoints - currentItem.currentCost
        rawStats = when (upgradeId) {
            1 -> rawStats.copy(auraPoints = newAura, jawLevel1 = rawStats.jawLevel1 + 1)
            2 -> rawStats.copy(auraPoints = newAura, jawLevel2 = rawStats.jawLevel2 + 1)
            3 -> rawStats.copy(auraPoints = newAura, jawLevel3 = rawStats.jawLevel3 + 1)
            4 -> rawStats.copy(auraPoints = newAura, jawLevel4 = rawStats.jawLevel4 + 1)
            5 -> rawStats.copy(auraPoints = newAura, jawLevel5 = rawStats.jawLevel5 + 1)
            6 -> rawStats.copy(auraPoints = newAura, jawLevel6 = rawStats.jawLevel6 + 1)
            7 -> rawStats.copy(auraPoints = newAura, jawLevel7 = rawStats.jawLevel7 + 1)
            else -> rawStats
        }

        vibrate(40)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun buyPassiveUpgrade(upgradeId: Int) {
        val currentItem = _uiState.value.passiveUpgrades.find { it.def.id == upgradeId } ?: return
        if (rawStats.auraPoints < currentItem.currentCost) return

        val newAura = rawStats.auraPoints - currentItem.currentCost
        rawStats = when (upgradeId) {
            1 -> rawStats.copy(auraPoints = newAura, passiveLevel1 = rawStats.passiveLevel1 + 1)
            2 -> rawStats.copy(auraPoints = newAura, passiveLevel2 = rawStats.passiveLevel2 + 1)
            3 -> rawStats.copy(auraPoints = newAura, passiveLevel3 = rawStats.passiveLevel3 + 1)
            4 -> rawStats.copy(auraPoints = newAura, passiveLevel4 = rawStats.passiveLevel4 + 1)
            5 -> rawStats.copy(auraPoints = newAura, passiveLevel5 = rawStats.passiveLevel5 + 1)
            6 -> rawStats.copy(auraPoints = newAura, passiveLevel6 = rawStats.passiveLevel6 + 1)
            7 -> rawStats.copy(auraPoints = newAura, passiveLevel7 = rawStats.passiveLevel7 + 1)
            else -> rawStats
        }

        vibrate(40)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun buyKochUpgrade(upgradeId: Int) {
        val currentItem = _uiState.value.kochUpgrades.find { it.def.id == upgradeId } ?: return
        if (rawStats.auraPoints < currentItem.currentCost) return

        val newAura = rawStats.auraPoints - currentItem.currentCost
        rawStats = when (upgradeId) {
            1 -> rawStats.copy(auraPoints = newAura, kochLevel1 = rawStats.kochLevel1 + 1)
            2 -> rawStats.copy(auraPoints = newAura, kochLevel2 = rawStats.kochLevel2 + 1)
            3 -> rawStats.copy(auraPoints = newAura, kochLevel3 = rawStats.kochLevel3 + 1)
            4 -> rawStats.copy(auraPoints = newAura, kochLevel4 = rawStats.kochLevel4 + 1)
            else -> rawStats
        }

        vibrate(50)
        recalculateState()
        saveCurrentStats()
    }

    fun activateKochMode() {
        if (_uiState.value.isKochModeActive) return

        // Requires either 100% adrenaline or resets it
        rawStats = rawStats.copy(kochAdrenaline = 0f)
        val spiritBonus = rawStats.kochLevel4 * 5
        val durationSec = 20 + spiritBonus

        _uiState.update { it.copy(isKochModeActive = true, kochModeRemainingSec = durationSec) }
        recalculateState()
        vibrate(100)
        if (_uiState.value.soundEnabled) SoundManager.playKochModeRoar()

        kochTimerJob?.cancel()
        kochTimerJob = viewModelScope.launch {
            for (sec in durationSec downTo 1) {
                _uiState.update { it.copy(kochModeRemainingSec = sec) }
                delay(1000)
            }
            _uiState.update { it.copy(isKochModeActive = false, kochModeRemainingSec = 0) }
            recalculateState()
        }
    }

    fun evolve() {
        val nextEvo = _uiState.value.nextEvolution ?: return
        if (rawStats.auraPoints < nextEvo.reqAura) return

        val newStage = rawStats.evolutionStage + 1
        rawStats = rawStats.copy(
            evolutionStage = newStage
        )

        vibrate(150)
        if (_uiState.value.soundEnabled) SoundManager.playEvolutionFanfare()
        recalculateState()
        saveCurrentStats()

        _uiState.update {
            it.copy(
                showEvolutionCelebration = true,
                justEvolvedStage = EvolutionStages.getStage(newStage)
            )
        }
    }

    fun dismissCelebration() {
        _uiState.update { it.copy(showEvolutionCelebration = false) }
    }

    fun dismissOfflineDialog() {
        _uiState.update { it.copy(offlineAuraEarned = null) }
    }

    fun toggleHaptics() {
        val newHaptics = !rawStats.hapticsEnabled
        rawStats = rawStats.copy(hapticsEnabled = newHaptics)
        _uiState.update { it.copy(hapticsEnabled = newHaptics) }
        saveCurrentStats()
    }

    fun toggleSound() {
        val newSound = !_uiState.value.soundEnabled
        _uiState.update { it.copy(soundEnabled = newSound) }
    }

    fun claimAchievement(achievementId: String) {
        if (claimedAchievementIds.contains(achievementId)) return
        val item = _uiState.value.achievements.find { it.def.id == achievementId } ?: return
        if (!item.isUnlocked) return

        claimedAchievementIds.add(achievementId)
        val reward = item.def.rewardAura
        rawStats = rawStats.copy(
            auraPoints = rawStats.auraPoints + reward,
            totalAuraEarned = rawStats.totalAuraEarned + reward
        )

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "ДОСТИЖЕНИЕ! +${FormatUtil.formatNumber(reward)}",
            x = 180f,
            y = 200f,
            colorHex = 0xFFFFD600
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(80)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun selectSkin(skinId: String) {
        val unlockedSet = rawStats.unlockedSkinIds.split(",").toSet()
        val skinDef = CharacterSkinCatalog.getSkin(skinId)
        if (!unlockedSet.contains(skinId) && skinDef.costAura > 0.0) return

        rawStats = rawStats.copy(selectedSkinId = skinId)
        vibrate(40)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun buySkin(skinId: String) {
        val skinDef = CharacterSkinCatalog.getSkin(skinId)
        if (rawStats.auraPoints < skinDef.costAura) return
        if (rawStats.evolutionStage < skinDef.reqStage) return

        val unlockedList = rawStats.unlockedSkinIds.split(",").toMutableList()
        if (!unlockedList.contains(skinId)) {
            unlockedList.add(skinId)
        }

        rawStats = rawStats.copy(
            auraPoints = rawStats.auraPoints - skinDef.costAura,
            selectedSkinId = skinId,
            unlockedSkinIds = unlockedList.joinToString(",")
        )

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "СКИН РАЗБЛОКИРОВАН! 👑",
            x = 200f,
            y = 220f,
            colorHex = 0xFFFFD600
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(100)
        if (_uiState.value.soundEnabled) SoundManager.playEvolutionFanfare()
        recalculateState()
        saveCurrentStats()
    }

    private fun vibrate(durationMs: Long) {
        if (!rawStats.hapticsEnabled || vibrator == null || !vibrator.hasVibrator()) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        } catch (_: Exception) {}
    }

    private fun saveCurrentStats() {
        viewModelScope.launch {
            repository.saveStats(rawStats)
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveCurrentStats()
    }
}
