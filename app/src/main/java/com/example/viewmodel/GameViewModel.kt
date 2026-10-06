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
import com.example.model.CarAsset
import com.example.model.CharacterSkinCatalog
import com.example.model.CryptoCoin
import com.example.model.EvolutionStageInfo
import com.example.model.EvolutionStages
import com.example.model.GameStats
import com.example.model.JawUpgradeDef
import com.example.model.JawUpgradeItem
import com.example.model.JawUpgradesCatalog
import com.example.model.KochUpgradeDef
import com.example.model.KochUpgradeItem
import com.example.model.KochUpgradesCatalog
import com.example.model.LifestyleCatalog
import com.example.model.PassiveUpgradeDef
import com.example.model.PassiveUpgradeItem
import com.example.model.PassiveUpgradesCatalog
import com.example.model.PlateRarity
import com.example.model.RealEstateAsset
import com.example.model.RussianPlate
import com.example.model.SkinDef
import com.example.model.SkinItem
import com.example.model.BanyaHybrid
import com.example.model.BoxReward
import com.example.model.BoxType
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
    val ownedPlates: List<RussianPlate> = listOf(RussianPlate("О 741 ТР 77", "О", "741", "ТР", "77", PlateRarity.COMMON, "Обычный городской госномер", 0.05, 0.05)),
    val brawlTrophies: Long = 0L,
    val brawlRank: Int = 1,
    val particles: List<ClickParticle> = emptyList(),
    val hapticsEnabled: Boolean = true,
    val showEvolutionCelebration: Boolean = false,
    val justEvolvedStage: EvolutionStageInfo? = null,
    val bonesmashProgress: Float = 0f, // 0f..1f for jaw remodeling bar
    val soundEnabled: Boolean = true,
    val achievements: List<AchievementItem> = emptyList(),
    val claimedAchievementIds: Set<String> = emptySet(),
    val activeSkin: SkinDef = CharacterSkinCatalog.SKINS.first(),
    val unlockedCharacterIds: Set<String> = setOf("kyrgyz_anton", "gleb_sportik", "zahar_baryga", "kochvalds", "vlados", "penisov", "skuf", "durov", "maga_borzuha", "boris_ofnik"),
    val skins: List<SkinItem> = emptyList(),
    val ownedCarIds: Set<String> = emptySet(),
    val equippedCarId: String? = null,
    val ownedRealEstateIds: Set<String> = emptySet(),
    val cryptoCoins: List<CryptoCoin> = LifestyleCatalog.INITIAL_CRYPTO,
    val equippedPlate: RussianPlate = RussianPlate("О 741 ТР 77", "О", "741", "ТР", "77", PlateRarity.COMMON, "Обычный городской госномер", 0.05, 0.05),
    val plateSpinCost: Double = 10000.0,
    val tokens: Long = 100L,
    val gems: Int = 30,
    val banyaHybrids: List<BanyaHybrid> = emptyList(),
    val openedBoxesCount: Int = 0,
    val showBoxRewardDialog: Boolean = false,
    val currentBoxRewards: List<BoxReward> = emptyList(),
    val currentOpeningBoxType: BoxType? = null,
    val showBanyaCelebration: Boolean = false,
    val latestBanyaHybrid: BanyaHybrid? = null
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

            // Load saved owned cars, estates, plates, and crypto
            val parsedCarIds = if (savedStats.ownedCarIds.isNotBlank()) savedStats.ownedCarIds.split(",").filter { it.isNotBlank() }.toSet() else setOf("vaz_2107")
            val parsedEquippedCar = if (savedStats.equippedCarId.isNotBlank()) savedStats.equippedCarId else "vaz_2107"
            val parsedRealEstateIds = if (savedStats.ownedRealEstateIds.isNotBlank()) savedStats.ownedRealEstateIds.split(",").filter { it.isNotBlank() }.toSet() else emptySet()

            val defaultInitialPlate = RussianPlate("О 741 ТР 77", "О", "741", "ТР", "77", PlateRarity.COMMON, "Обычный городской госномер", 0.05, 0.05)
            val parsedPlates = if (savedStats.ownedPlates.isNotBlank()) {
                val list = savedStats.ownedPlates.split(";").mapNotNull { deserializePlate(it) }
                if (list.isNotEmpty()) list else listOf(defaultInitialPlate)
            } else listOf(defaultInitialPlate)

            val parsedEquippedPlate = deserializePlate(savedStats.equippedPlate) ?: parsedPlates.firstOrNull() ?: defaultInitialPlate

            val cryptoMap = if (savedStats.cryptoBalances.isNotBlank()) {
                savedStats.cryptoBalances.split(";").mapNotNull {
                    val p = it.split(":")
                    if (p.size == 2) p[0] to (p[1].toDoubleOrNull() ?: 0.0) else null
                }.toMap()
            } else emptyMap()

            val initialCrypto = LifestyleCatalog.INITIAL_CRYPTO.map { coin ->
                val savedAmount = cryptoMap[coin.symbol] ?: 0.0
                coin.copy(ownedAmount = savedAmount)
            }

            val starterChars = setOf("kyrgyz_anton", "gleb_sportik", "zahar_baryga", "kochvalds", "vlados", "penisov", "skuf", "durov", "maga_borzuha", "boris_ofnik")
            val parsedChars = if (savedStats.unlockedCharacterIds.isNotBlank()) {
                (starterChars + savedStats.unlockedCharacterIds.split(",").filter { it.isNotBlank() }.toSet())
            } else starterChars

            _uiState.update { current ->
                current.copy(
                    ownedCarIds = parsedCarIds,
                    equippedCarId = parsedEquippedCar,
                    ownedRealEstateIds = parsedRealEstateIds,
                    ownedPlates = parsedPlates,
                    equippedPlate = parsedEquippedPlate,
                    cryptoCoins = initialCrypto,
                    unlockedCharacterIds = parsedChars
                )
            }

            recalculateState()
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

        // Live Crypto Market Loop (Fluctuates prices every 6 seconds)
        viewModelScope.launch {
            while (isActive) {
                delay(6000L)
                _uiState.update { current ->
                    current.copy(
                        cryptoCoins = current.cryptoCoins.map { coin ->
                            val deltaPercent = (Random.nextDouble(-4.5, 6.5))
                            val newPrice = (coin.currentPriceAura * (1.0 + deltaPercent / 100.0)).coerceAtLeast(1.0)
                            coin.copy(
                                currentPriceAura = newPrice,
                                change24hPercent = (coin.change24hPercent + deltaPercent * 0.4).coerceIn(-50.0, 150.0)
                            )
                        }
                    )
                }
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
            rawStats.jawLevel7,
            rawStats.jawLevel8
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

        // Lifestyle Multipliers (Car, Real Estate, Russian Plate)
        val equippedCar = LifestyleCatalog.CARS.find { it.id == _uiState.value.equippedCarId }
        val carClickMult = 1.0 + (equippedCar?.clickMultiplierBonus ?: 0.0)
        val carPassiveMult = 1.0 + (equippedCar?.passiveMultiplierBonus ?: 0.0)

        val realEstateAuraSec = _uiState.value.ownedRealEstateIds.sumOf { id ->
            LifestyleCatalog.REAL_ESTATE.find { it.id == id }?.passiveAuraPerSec ?: 0.0
        }
        val realEstateMult = 1.0 + _uiState.value.ownedRealEstateIds.sumOf { id ->
            LifestyleCatalog.REAL_ESTATE.find { it.id == id }?.auraMultiplierBonus ?: 0.0
        }

        val plateClickMult = 1.0 + _uiState.value.equippedPlate.clickBonus
        val platePassiveMult = 1.0 + _uiState.value.equippedPlate.passiveBonus

        val hybrids = parseBanyaHybrids(rawStats.banyaHybrids)
        val hybridClickMult = 1.0 + (hybrids.size * 0.20)
        val hybridPassiveMult = 1.0 + (hybrids.size * 0.20)

        val finalClickPower = totalBaseClickPower * currentEvo.clickMultiplier * skinClickMult * kochClickMultiplier * comboMult * kochActiveMultiplier * carClickMult * plateClickMult * hybridClickMult
        val finalAuraSec = ((totalBasePassive * currentEvo.passiveMultiplier * skinPassiveMult * kochPassiveMultiplier * (if (_uiState.value.isKochModeActive) 2.0 else 1.0) * carPassiveMult * platePassiveMult * realEstateMult) + realEstateAuraSec) * hybridPassiveMult

        val canEvolve = nextEvo != null && rawStats.auraPoints >= nextEvo.reqAura

        val bonesmashProgress = ((rawStats.bonesmashHits % 50).toFloat() / 50f).coerceIn(0f, 1f)

        val unlockedChars = _uiState.value.unlockedCharacterIds
        val unlockedSkinIdsSet = rawStats.unlockedSkinIds.split(",").toSet()
        val allSkins = CharacterSkinCatalog.SKINS.map { def ->
            val isCharUnlocked = def.characterId in unlockedChars
            val isSkinUnlocked = isCharUnlocked && (unlockedSkinIdsSet.contains(def.id) || (def.costAura == 0.0 && def.reqStage <= rawStats.evolutionStage))
            SkinItem(
                def = def,
                isUnlocked = isSkinUnlocked,
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
                "boris_unlocked" -> rawStats.selectedSkinId.startsWith("boris") || unlockedSkinIdsSet.any { it.startsWith("boris") }
                "vaska_unlocked" -> rawStats.selectedSkinId.startsWith("vaska") || unlockedSkinIdsSet.any { it.startsWith("vaska") }
                "skin_collector" -> unlockedSkinIdsSet.size >= 5
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

        val ownedCarsCount = _uiState.value.ownedCarIds.size
        val ownedPlatesCount = _uiState.value.ownedPlates.size
        val banyaHybridsCount = hybrids.size
        val trophies = (rawStats.totalMogs / 10 + rawStats.bonesmashHits / 5 + ownedCarsCount * 50 + ownedPlatesCount * 100 + banyaHybridsCount * 150 + rawStats.evolutionStage * 250).coerceAtLeast(0L)
        val rank = ((trophies / 300) + 1).toInt().coerceIn(1, 35)

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
                skins = allSkins,
                tokens = rawStats.tokens,
                gems = rawStats.gems,
                banyaHybrids = hybrids,
                openedBoxesCount = rawStats.boxesOpened,
                brawlTrophies = trophies,
                brawlRank = rank
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
        val tokenGain = if (Random.nextInt(100) < 35) 1L else 0L
        val newTokens = rawStats.tokens + tokenGain

        rawStats = rawStats.copy(
            auraPoints = newAura,
            totalAuraEarned = newTotalAura,
            totalMogs = newMogs,
            kochAdrenaline = newAdrenaline,
            tokens = newTokens
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
            kochAdrenaline = newAdrenaline,
            tokens = rawStats.tokens + 3L
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
            8 -> rawStats.copy(auraPoints = newAura, jawLevel8 = rawStats.jawLevel8 + 1)
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
        // Daily / offline reward removed as requested
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
        val skinDef = CharacterSkinCatalog.getSkin(skinId)
        if (skinDef.characterId !in _uiState.value.unlockedCharacterIds) return
        val unlockedSet = rawStats.unlockedSkinIds.split(",").toSet()
        if (!unlockedSet.contains(skinId) && skinDef.costAura > 0.0) return

        rawStats = rawStats.copy(selectedSkinId = skinId)
        vibrate(40)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun buySkin(skinId: String) {
        val skinDef = CharacterSkinCatalog.getSkin(skinId)
        if (skinDef.characterId !in _uiState.value.unlockedCharacterIds) return
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

    fun buyCar(carId: String) {
        val car = LifestyleCatalog.CARS.find { it.id == carId } ?: return
        if (rawStats.auraPoints < car.costAura) return
        if (_uiState.value.ownedCarIds.contains(carId)) return

        rawStats = rawStats.copy(auraPoints = rawStats.auraPoints - car.costAura)
        _uiState.update { current ->
            current.copy(
                ownedCarIds = current.ownedCarIds + carId,
                equippedCarId = carId
            )
        }

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "ТАЧКА В ГАРАЖЕ! ${car.iconEmoji}",
            x = 200f,
            y = 200f,
            colorHex = 0xFFFFD600
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(80)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun equipCar(carId: String) {
        if (!_uiState.value.ownedCarIds.contains(carId)) return
        _uiState.update { it.copy(equippedCarId = carId) }
        recalculateState()
    }

    fun buyRealEstate(estateId: String) {
        val estate = LifestyleCatalog.REAL_ESTATE.find { it.id == estateId } ?: return
        if (rawStats.auraPoints < estate.costAura) return
        if (_uiState.value.ownedRealEstateIds.contains(estateId)) return

        rawStats = rawStats.copy(auraPoints = rawStats.auraPoints - estate.costAura)
        _uiState.update { current ->
            current.copy(ownedRealEstateIds = current.ownedRealEstateIds + estateId)
        }

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "НЕДВИЖКА КУПЛЕНА! ${estate.iconEmoji}",
            x = 200f,
            y = 200f,
            colorHex = 0xFF00E5FF
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(100)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun buyCrypto(symbol: String, amountAura: Double) {
        if (rawStats.auraPoints < amountAura || amountAura <= 0) return
        val coin = _uiState.value.cryptoCoins.find { it.symbol == symbol } ?: return
        val coinsBought = amountAura / coin.currentPriceAura

        rawStats = rawStats.copy(auraPoints = rawStats.auraPoints - amountAura)
        _uiState.update { current ->
            current.copy(
                cryptoCoins = current.cryptoCoins.map {
                    if (it.symbol == symbol) it.copy(ownedAmount = it.ownedAmount + coinsBought) else it
                }
            )
        }

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "+${String.format("%.3f", coinsBought)} $symbol! 📈",
            x = 200f,
            y = 220f,
            colorHex = 0xFF00E676
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(40)
        recalculateState()
        saveCurrentStats()
    }

    fun buyAllInCrypto(symbol: String) {
        val allAura = rawStats.auraPoints
        if (allAura <= 0) return
        buyCrypto(symbol, allAura)
    }

    fun sellCrypto(symbol: String, coinAmount: Double) {
        val coin = _uiState.value.cryptoCoins.find { it.symbol == symbol } ?: return
        if (coin.ownedAmount < coinAmount || coinAmount <= 0) return
        val auraGained = coinAmount * coin.currentPriceAura

        rawStats = rawStats.copy(
            auraPoints = rawStats.auraPoints + auraGained,
            totalAuraEarned = rawStats.totalAuraEarned + auraGained
        )
        _uiState.update { current ->
            current.copy(
                cryptoCoins = current.cryptoCoins.map {
                    if (it.symbol == symbol) it.copy(ownedAmount = (it.ownedAmount - coinAmount).coerceAtLeast(0.0)) else it
                }
            )
        }

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "ПРОФИТ! +${FormatUtil.formatNumber(auraGained)} АУРЫ 💰",
            x = 200f,
            y = 220f,
            colorHex = 0xFFFFD600
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(60)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun spinRussianPlate() {
        val cost = _uiState.value.plateSpinCost
        if (rawStats.auraPoints < cost) return

        rawStats = rawStats.copy(auraPoints = rawStats.auraPoints - cost)
        val newPlate = LifestyleCatalog.spinRandomPlate()

        _uiState.update { current ->
            val existing = current.ownedPlates
            val updated = if (existing.any { it.fullPlate == newPlate.fullPlate }) existing else (existing + newPlate)
            current.copy(
                equippedPlate = newPlate,
                ownedPlates = updated
            )
        }

        val particle = ClickParticle(
            id = ++particleCounter,
            text = "ГОСНОМЕР: ${newPlate.fullPlate}! 🚗",
            x = 180f,
            y = 200f,
            colorHex = newPlate.rarity.colorHex
        )
        _uiState.update { it.copy(particles = it.particles + particle) }

        vibrate(120)
        if (_uiState.value.soundEnabled) SoundManager.playLevelUp()
        recalculateState()
        saveCurrentStats()
    }

    fun equipPlate(fullPlate: String) {
        val plate = _uiState.value.ownedPlates.find { it.fullPlate == fullPlate } ?: return
        _uiState.update { it.copy(equippedPlate = plate) }
        recalculateState()
        saveCurrentStats()
    }

    fun openBox(boxType: BoxType) {
        val canTokens = boxType.tokenCost > 0 && rawStats.tokens >= boxType.tokenCost
        val canGems = boxType.gemCost > 0 && rawStats.gems >= boxType.gemCost
        val canAura = rawStats.auraPoints >= boxType.auraCost

        if (!canTokens && !canGems && !canAura) return

        rawStats = when {
            canTokens -> rawStats.copy(tokens = rawStats.tokens - boxType.tokenCost)
            canGems -> rawStats.copy(gems = rawStats.gems - boxType.gemCost)
            else -> rawStats.copy(auraPoints = rawStats.auraPoints - boxType.auraCost)
        }

        val rewards = mutableListOf<BoxReward>()
        val mult = if (boxType == BoxType.MEGA_BOX) 5 else if (boxType == BoxType.BIG_BOX) 2 else 1

        // Check for new fighter drop from boxes
        val unlockedChars = _uiState.value.unlockedCharacterIds
        val lockedChars = CharacterSkinCatalog.CHARACTERS.filter { it.id !in unlockedChars }
        val dropChancePercent = when (boxType) {
            BoxType.MEGA_BOX -> 85
            BoxType.BIG_BOX -> 50
            BoxType.BRAWL_BOX -> 25
        }
        if (lockedChars.isNotEmpty() && Random.nextInt(100) < dropChancePercent) {
            val droppedChar = lockedChars.random()
            val newUnlockedChars = unlockedChars + droppedChar.id
            val defaultSkin = droppedChar.skins.firstOrNull()?.id
            val newUnlockedSkins = if (defaultSkin != null && defaultSkin !in rawStats.unlockedSkinIds) {
                "${rawStats.unlockedSkinIds},$defaultSkin"
            } else rawStats.unlockedSkinIds
            rawStats = rawStats.copy(
                unlockedCharacterIds = newUnlockedChars.joinToString(","),
                unlockedSkinIds = newUnlockedSkins
            )
            _uiState.update { it.copy(unlockedCharacterIds = newUnlockedChars) }
            rewards.add(0, BoxReward(
                title = "НОВЫЙ БОЕЦ! 🎉",
                amountText = droppedChar.name,
                iconEmoji = "👑",
                colorHex = droppedChar.rarity.colorHex,
                unlockedCharacter = droppedChar
            ))
        }

        repeat(boxType.rewardsCount) { idx ->
            when (Random.nextInt(4)) {
                0 -> {
                    val auraWin = 8000.0 * (idx + 1) * mult
                    rawStats = rawStats.copy(auraPoints = rawStats.auraPoints + auraWin, totalAuraEarned = rawStats.totalAuraEarned + auraWin)
                    rewards.add(BoxReward("Куш Ауры", "+${FormatUtil.formatNumber(auraWin)} ⚡", "⚡", 0xFF00E5FF))
                }
                1 -> {
                    val gemsWin = Random.nextInt(3, 10) * mult
                    rawStats = rawStats.copy(gems = rawStats.gems + gemsWin)
                    rewards.add(BoxReward("Кристаллы", "+$gemsWin 💎", "💎", 0xFFE040FB))
                }
                2 -> {
                    val tokensWin = Random.nextInt(40, 150) * mult
                    rawStats = rawStats.copy(tokens = rawStats.tokens + tokensWin)
                    rewards.add(BoxReward("Жетоны Бро", "+$tokensWin 🎟️", "🎟️", 0xFFFFD600))
                }
                else -> {
                    val adrenWin = Random.nextInt(15, 35).toFloat()
                    rawStats = rawStats.copy(kochAdrenaline = (rawStats.kochAdrenaline + adrenWin).coerceAtMost(100f))
                    rewards.add(BoxReward("Банный Веник", "+${adrenWin.toInt()}% Адреналин 🔥", "🌿", 0xFFFF5722))
                }
            }
        }

        rawStats = rawStats.copy(boxesOpened = rawStats.boxesOpened + 1)
        if (_uiState.value.soundEnabled) SoundManager.playBoxOpen()
        vibrate(60)

        _uiState.update {
            it.copy(
                showBoxRewardDialog = true,
                currentBoxRewards = rewards,
                currentOpeningBoxType = boxType
            )
        }
        recalculateState()
        saveCurrentStats()
    }

    fun dismissBoxRewardDialog() {
        _uiState.update { it.copy(showBoxRewardDialog = false, currentBoxRewards = emptyList()) }
    }

    fun fuseCharactersInBanya(charAId: String, charBId: String) {
        val costAura = 25000.0
        val costTokens = 50L
        if (rawStats.auraPoints < costAura || rawStats.tokens < costTokens) return
        if (charAId == charBId) return

        val charA = CharacterSkinCatalog.CHARACTERS.find { it.id == charAId } ?: return
        val charB = CharacterSkinCatalog.CHARACTERS.find { it.id == charBId } ?: return

        val hybridKey = "${charAId}+${charBId}"
        val existingList = rawStats.banyaHybrids.split(",").filter { it.isNotBlank() }
        val newHybridsStr = if (hybridKey in existingList) rawStats.banyaHybrids else {
            (existingList + hybridKey).joinToString(",")
        }

        val nameA = charA.name.split(" ").first()
        val nameB = charB.name.split(" ").first()
        val hybrid = BanyaHybrid(
            id = hybridKey,
            name = "$nameA-$nameB (Банный Сигма)",
            parentAId = charAId,
            parentBId = charBId,
            description = "Выкован в русской бане при 110°C на березовом пару! Двойная мощь ${charA.name} и ${charB.name}.",
            iconEmoji = "🧖‍♂️",
            clickMultiplierBonus = 1.25,
            passiveMultiplierBonus = 1.25,
            steamTempC = Random.nextInt(105, 125)
        )

        rawStats = rawStats.copy(
            auraPoints = rawStats.auraPoints - costAura,
            tokens = rawStats.tokens - costTokens,
            banyaHybrids = newHybridsStr
        )

        if (_uiState.value.soundEnabled) {
            SoundManager.playBanyaSteam()
            SoundManager.playVenikHit()
        }
        vibrate(80)

        _uiState.update {
            it.copy(
                showBanyaCelebration = true,
                latestBanyaHybrid = hybrid
            )
        }
        recalculateState()
        saveCurrentStats()
    }

    fun dismissBanyaCelebration() {
        _uiState.update { it.copy(showBanyaCelebration = false, latestBanyaHybrid = null) }
    }

    private fun parseBanyaHybrids(rawString: String): List<BanyaHybrid> {
        if (rawString.isBlank()) return emptyList()
        return rawString.split(",").mapNotNull { entry ->
            val parts = entry.split("+")
            if (parts.size >= 2) {
                val charA = CharacterSkinCatalog.CHARACTERS.find { it.id == parts[0] }
                val charB = CharacterSkinCatalog.CHARACTERS.find { it.id == parts[1] }
                if (charA != null && charB != null) {
                    val nameA = charA.name.split(" ").first()
                    val nameB = charB.name.split(" ").first()
                    BanyaHybrid(
                        id = entry,
                        name = "$nameA-$nameB (Банный Сигма)",
                        parentAId = charA.id,
                        parentBId = charB.id,
                        description = "Выкован в русской бане при 110°C на березовом пару.",
                        iconEmoji = "🧖‍♂️",
                        clickMultiplierBonus = 1.25,
                        passiveMultiplierBonus = 1.25,
                        steamTempC = 110
                    )
                } else null
            } else null
        }
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

    private fun serializePlate(p: RussianPlate): String =
        "${p.fullPlate}|${p.seriesFirst}|${p.number}|${p.seriesRest}|${p.region}|${p.rarity.name}|${p.specialName}|${p.clickBonus}|${p.passiveBonus}"

    private fun deserializePlate(s: String): RussianPlate? {
        val parts = s.split("|")
        if (parts.size < 9) return null
        val rarity = try { PlateRarity.valueOf(parts[5]) } catch (_: Exception) { PlateRarity.COMMON }
        return RussianPlate(
            fullPlate = parts[0],
            seriesFirst = parts[1],
            number = parts[2],
            seriesRest = parts[3],
            region = parts[4],
            rarity = rarity,
            specialName = parts[6],
            clickBonus = parts[7].toDoubleOrNull() ?: 0.05,
            passiveBonus = parts[8].toDoubleOrNull() ?: 0.05
        )
    }

    private fun saveCurrentStats() {
        viewModelScope.launch {
            val ownedCarsStr = _uiState.value.ownedCarIds.joinToString(",")
            val equippedCarStr = _uiState.value.equippedCarId ?: "vaz_2107"
            val ownedRealEstateStr = _uiState.value.ownedRealEstateIds.joinToString(",")
            val ownedPlatesStr = _uiState.value.ownedPlates.joinToString(";") { serializePlate(it) }
            val equippedPlateStr = serializePlate(_uiState.value.equippedPlate)
            val cryptoBalancesStr = _uiState.value.cryptoCoins.joinToString(";") { "${it.symbol}:${it.ownedAmount}" }

            val updatedStats = rawStats.copy(
                ownedCarIds = ownedCarsStr,
                equippedCarId = equippedCarStr,
                ownedRealEstateIds = ownedRealEstateStr,
                ownedPlates = ownedPlatesStr,
                equippedPlate = equippedPlateStr,
                cryptoBalances = cryptoBalancesStr,
                unlockedCharacterIds = _uiState.value.unlockedCharacterIds.joinToString(","),
                lastTimestamp = System.currentTimeMillis()
            )
            rawStats = updatedStats
            repository.saveStats(updatedStats)
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveCurrentStats()
    }
}
