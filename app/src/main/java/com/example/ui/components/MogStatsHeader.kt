package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BonesmashCrimson
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.util.FormatUtil
import com.example.viewmodel.GameUiState

@Composable
fun MogStatsHeader(
    uiState: GameUiState,
    onToggleHaptics: () -> Unit,
    onToggleSound: () -> Unit,
    onOpenAchievements: () -> Unit,
    onOpenScanner: () -> Unit,
    onOpenGithubGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    val claimableCount = uiState.achievements.count { it.isUnlocked && !it.isClaimed }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .border(
                1.dp,
                if (uiState.isKochModeActive) BonesmashCrimson else DarkCardBorder,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            ),
        color = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            // Top action bar: Evolution badge + utility icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Evolution badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    ElectricPurple.copy(alpha = 0.3f),
                                    NeonCyan.copy(alpha = 0.2f)
                                )
                            )
                        )
                        .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "👑 ${uiState.currentEvolution.nameRu}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "PSL ${uiState.currentEvolution.pslScore}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = PureGold
                        )
                    }
                }

                // Utility buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Craniofacial Scanner
                    IconButton(
                        onClick = onOpenScanner,
                        modifier = Modifier.size(34.dp).testTag("header_scanner_button")
                    ) {
                        Text(text = "📐", fontSize = 16.sp)
                    }

                    // Achievements with badge
                    IconButton(
                        onClick = onOpenAchievements,
                        modifier = Modifier.size(34.dp).testTag("header_achievements_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (claimableCount > 0) {
                                    Badge(containerColor = PureGold, contentColor = Color.Black) {
                                        Text(text = claimableCount.toString(), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Достижения",
                                tint = if (claimableCount > 0) PureGold else TextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Sound Toggle
                    IconButton(
                        onClick = onToggleSound,
                        modifier = Modifier.size(34.dp).testTag("toggle_sound_button")
                    ) {
                        Icon(
                            imageVector = if (uiState.soundEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                            contentDescription = "Звуковые эффекты",
                            tint = if (uiState.soundEnabled) NeonCyan else TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Haptics Toggle
                    IconButton(
                        onClick = onToggleHaptics,
                        modifier = Modifier.size(34.dp).testTag("toggle_haptics_button")
                    ) {
                        Icon(
                            imageVector = if (uiState.hapticsEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = "Вибрация",
                            tint = if (uiState.hapticsEnabled) ElectricPurple else TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // GitHub guide
                    IconButton(
                        onClick = onOpenGithubGuide,
                        modifier = Modifier.size(34.dp).testTag("open_github_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "GitHub",
                            tint = PureGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Main Aura counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "ОЧКИ МОГГИНГА (АУРА)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = FormatUtil.formatNumber(uiState.auraPoints),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black,
                            color = if (uiState.isKochModeActive) BonesmashCrimson else NeonCyan,
                            modifier = Modifier.testTag("aura_points_display")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "✨",
                            fontSize = 18.sp
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "В СЕКУНДУ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "+${FormatUtil.formatNumber(uiState.auraPerSec)}/с",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricPurple
                    )
                    Text(
                        text = "Клик: +${FormatUtil.formatNumber(uiState.clickPower)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PureGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Adrenaline bar for "Кочнуть"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = if (uiState.isKochModeActive) BonesmashCrimson else PureGold,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (uiState.isKochModeActive) "🔥 РЕЖИМ КОЧАЛКИ (${uiState.kochModeRemainingSec}с)" else "Энергия Кочалки (Адреналин)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isKochModeActive) BonesmashCrimson else TextSecondary
                    )
                }

                if (uiState.currentCombo > 1) {
                    Text(
                        text = "COMBO x${uiState.currentCombo}! (${String.format(java.util.Locale.US, "%.1f", uiState.comboMultiplier)}x)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            val progress = (uiState.kochAdrenaline / 100f).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { if (uiState.isKochModeActive) 1f else progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (uiState.isKochModeActive) BonesmashCrimson else PureGold,
                trackColor = DarkCardBorder
            )
        }
    }
}
