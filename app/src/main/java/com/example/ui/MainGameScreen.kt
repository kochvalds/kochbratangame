package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AttractivenessSection
import com.example.ui.components.BonesmashSection
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.EvolutionProfileSection
import com.example.ui.components.FloatingParticlesOverlay
import com.example.ui.components.GitHubExportDialog
import com.example.ui.components.KochGymSection
import com.example.ui.components.MogStatsHeader
import com.example.ui.components.OfflineEarningDialog
import com.example.ui.components.VoidocFaceTarget
import com.example.ui.theme.BonesmashCrimson
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GameViewModel

enum class GameTab(val title: String, val testTag: String) {
    MOGGING("Моггинг", "tab_mogging"),
    JAW("Челюсть", "tab_jaw"),
    ATTRACTIVENESS("Аттрактив", "tab_attractiveness"),
    KOCH_GYM("Кочалка", "tab_koch_gym"),
    PROFILE("Профиль", "tab_profile")
}

@Composable
fun MainGameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentTab by remember { mutableStateOf(GameTab.MOGGING) }
    var showGithubDialog by remember { mutableStateOf(false) }

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
                onOpenGithubGuide = { showGithubDialog = true }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, DarkCardBorder)
            ) {
                GameTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    val tabIcon = when (tab) {
                        GameTab.MOGGING -> "🤫"
                        GameTab.JAW -> "🔨"
                        GameTab.ATTRACTIVENESS -> "✨"
                        GameTab.KOCH_GYM -> "💪"
                        GameTab.PROFILE -> "👑"
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Text(text = tabIcon, fontSize = 20.sp)
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = NeonCyan,
                            selectedTextColor = NeonCyan,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = NeonCyan.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag(tab.testTag)
                    )
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

    // Offline Earnings Dialog
    uiState.offlineAuraEarned?.let { earned ->
        OfflineEarningDialog(
            auraEarned = earned,
            onDismiss = viewModel::dismissOfflineDialog
        )
    }

    // GitHub Repo Guide Dialog
    if (showGithubDialog) {
        GitHubExportDialog(
            onDismiss = { showGithubDialog = false }
        )
    }
}
