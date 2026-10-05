package com.example.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AchievementsDialog
import com.example.ui.components.AttractivenessSection
import com.example.ui.components.BanyaSection
import com.example.ui.components.BonesmashSection
import com.example.ui.components.BoxesSection
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.CraniofacialScannerDialog
import com.example.ui.components.EvolutionProfileSection
import com.example.ui.components.FloatingParticlesOverlay
import com.example.ui.components.InventorySection
import com.example.ui.components.KochGymSection
import com.example.ui.components.LifestyleSection
import com.example.ui.components.MogStatsHeader
import com.example.ui.components.SkinsSection
import com.example.ui.components.VoidocFaceTarget
import com.example.ui.theme.BonesmashCrimson
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GameViewModel

enum class GameTab(val title: String, val iconEmoji: String, val testTag: String) {
    MOGGING("Моггинг", "🤫", "tab_mogging"),
    SKINS("Бойцы", "🎭", "tab_skins"),
    BANYA("Баня", "🧖‍♂️", "tab_banya"),
    BOXES("Ящики", "🎁", "tab_boxes"),
    INVENTORY("Инвентарь", "🎒", "tab_inventory"),
    LIFESTYLE("Люкс", "🏎️", "tab_lifestyle"),
    JAW("Челюсть", "🔨", "tab_jaw"),
    ATTRACTIVENESS("Аттрактив", "✨", "tab_attractiveness"),
    KOCH_GYM("Кочалка", "💪", "tab_koch_gym"),
    PROFILE("Профиль", "👑", "tab_profile")
}

@Composable
fun MainGameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentTab by remember { mutableStateOf(GameTab.MOGGING) }
    var showAchievementsDialog by remember { mutableStateOf(false) }
    var showScannerDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? Activity
    val onRotateScreen = {
        val current = activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        val next = when (current) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        activity?.requestedOrientation = next
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (uiState.isKochModeActive) {
                    Modifier.border(3.dp, BonesmashCrimson)
                } else Modifier
            ),
        containerColor = DarkBackground,
        topBar = {
            MogStatsHeader(
                uiState = uiState,
                onToggleHaptics = viewModel::toggleHaptics,
                onToggleSound = viewModel::toggleSound,
                onOpenAchievements = { showAchievementsDialog = true },
                onOpenScanner = { showScannerDialog = true },
                onRotateScreen = onRotateScreen
            )
        },
        bottomBar = {
            // Brawl Stars Style 3D Action Navigation Bar
            Surface(
                color = DarkSurface,
                tonalElevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PureGold.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GameTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        BrawlNavTabButton(
                            tab = tab,
                            isSelected = isSelected,
                            badgeCount = if (tab == GameTab.INVENTORY) (uiState.ownedPlates.size + uiState.ownedCarIds.size) else null,
                            onClick = { currentTab = tab }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                GameTab.MOGGING -> {
                    VoidocFaceTarget(
                        uiState = uiState,
                        onMogClick = viewModel::onMogClick,
                        onBonesmashClick = viewModel::onBonesmashClick,
                        onActivateKoch = viewModel::activateKochMode
                    )
                }
                GameTab.SKINS -> {
                    SkinsSection(
                        uiState = uiState,
                        skins = uiState.skins,
                        onSelectSkin = viewModel::selectSkin,
                        onBuySkin = viewModel::buySkin
                    )
                }
                GameTab.BANYA -> {
                    BanyaSection(
                        uiState = uiState,
                        onFuse = viewModel::fuseCharactersInBanya,
                        onDismissCelebration = viewModel::dismissBanyaCelebration
                    )
                }
                GameTab.BOXES -> {
                    BoxesSection(
                        uiState = uiState,
                        onOpenBox = viewModel::openBox,
                        onDismissReward = viewModel::dismissBoxRewardDialog
                    )
                }
                GameTab.INVENTORY -> {
                    InventorySection(
                        uiState = uiState,
                        onEquipPlate = viewModel::equipPlate,
                        onEquipCar = viewModel::equipCar
                    )
                }
                GameTab.LIFESTYLE -> {
                    LifestyleSection(
                        uiState = uiState,
                        onBuyCar = viewModel::buyCar,
                        onEquipCar = viewModel::equipCar,
                        onBuyRealEstate = viewModel::buyRealEstate,
                        onBuyCrypto = viewModel::buyCrypto,
                        onBuyAllInCrypto = viewModel::buyAllInCrypto,
                        onSellCrypto = viewModel::sellCrypto,
                        onSpinPlate = viewModel::spinRussianPlate
                    )
                }
                GameTab.JAW -> {
                    BonesmashSection(
                        uiState = uiState,
                        onBonesmashClick = viewModel::onBonesmashClick,
                        onBuyJawUpgrade = viewModel::buyJawUpgrade
                    )
                }
                GameTab.ATTRACTIVENESS -> {
                    AttractivenessSection(
                        uiState = uiState,
                        onBuyPassiveUpgrade = viewModel::buyPassiveUpgrade
                    )
                }
                GameTab.KOCH_GYM -> {
                    KochGymSection(
                        uiState = uiState,
                        onActivateKoch = viewModel::activateKochMode,
                        onBuyKochUpgrade = viewModel::buyKochUpgrade
                    )
                }
                GameTab.PROFILE -> {
                    EvolutionProfileSection(
                        uiState = uiState,
                        onEvolve = viewModel::evolve
                    )
                }
            }

            // Floating Click Meme Particles Overlay
            FloatingParticlesOverlay(particles = uiState.particles)
        }
    }

    // Celebration Dialog when evolving to a new stage
    if (uiState.showEvolutionCelebration && uiState.justEvolvedStage != null) {
        CelebrationDialog(
            stage = uiState.justEvolvedStage!!,
            onDismiss = viewModel::dismissCelebration
        )
    }

    // Achievements Dialog
    if (showAchievementsDialog) {
        AchievementsDialog(
            achievements = uiState.achievements,
            onClaim = viewModel::claimAchievement,
            onDismiss = { showAchievementsDialog = false }
        )
    }

    // Craniofacial Scanner Dialog
    if (showScannerDialog) {
        CraniofacialScannerDialog(
            uiState = uiState,
            onDismiss = { showScannerDialog = false }
        )
    }
}

@Composable
private fun BrawlNavTabButton(
    tab: GameTab,
    isSelected: Boolean,
    badgeCount: Int?,
    onClick: () -> Unit
) {
    val containerBrush = if (isSelected) {
        Brush.verticalGradient(
            listOf(PureGold, Color(0xFFFF8F00))
        )
    } else {
        Brush.verticalGradient(
            listOf(Color(0xFF263238), Color(0xFF1E2235))
        )
    }

    val borderColor = if (isSelected) Color(0xFFFFE082) else DarkCardBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerBrush)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag(tab.testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = tab.iconEmoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = tab.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                color = if (isSelected) Color.Black else TextPrimary
            )
            if (badgeCount != null && badgeCount > 0) {
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BonesmashCrimson)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "$badgeCount",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }
    }
}
