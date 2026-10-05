package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.ScreenRotation
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
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
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
    onRotateScreen: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val claimableCount = uiState.achievements.count { it.isUnlocked && !it.isClaimed }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
            .border(
                1.5.dp,
                if (uiState.isKochModeActive) BonesmashCrimson else PureGold.copy(alpha = 0.4f),
                RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
            ),
        color = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            // Brawl Stars Top Bar: Player profile on Left, Currency capsules in center/right, actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brawl Stars Player Profile Card (Avatar + Trophies + Rank)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF263238), Color(0xFF1A237E))
                            )
                        )
                        .border(1.5.dp, PureGold, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("header_profile_badge")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(PureGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🗿", fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Column {
                            Text(
                                text = uiState.activeSkin.name.take(14),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "🏆 ${uiState.brawlTrophies}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(BonesmashCrimson)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "Ранг ${uiState.brawlRank}",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Currency Badges (Brawl Stars Style Capsules)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tokens capsule
                    BrawlCurrencyCapsule(icon = "🎟️", value = "${uiState.tokens}", color = Color(0xFFFFD600))
                    // Gems capsule
                    BrawlCurrencyCapsule(icon = "💎", value = "${uiState.gems}", color = Color(0xFF00E5FF))
                }

                // Utility buttons: Rotate screen, Achievements, Sound, Scanner
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Screen Flip / Rotation Button (as requested by user!)
                    IconButton(
                        onClick = onRotateScreen,
                        modifier = Modifier.size(32.dp).testTag("header_rotate_screen_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ScreenRotation,
                            contentDescription = "Перевернуть экран",
                            tint = PureGold,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    // Achievements with badge
                    IconButton(
                        onClick = onOpenAchievements,
                        modifier = Modifier.size(32.dp).testTag("header_achievements_button")
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
                        modifier = Modifier.size(32.dp).testTag("toggle_sound_button")
                    ) {
                        Icon(
                            imageVector = if (uiState.soundEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                            contentDescription = "Звуковые эффекты",
                            tint = if (uiState.soundEnabled) NeonCyan else TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Craniofacial Scanner
                    IconButton(
                        onClick = onOpenScanner,
                        modifier = Modifier.size(32.dp).testTag("header_scanner_button")
                    ) {
                        Text(text = "📐", fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Aura / Gold Display with click and passive stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFB300)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⚡", fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "ОЧКИ МОГГИНГА (АУРА)",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = FormatUtil.formatNumber(uiState.auraPoints),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = if (uiState.isKochModeActive) BonesmashCrimson else NeonCyan,
                            modifier = Modifier.testTag("aura_points_display")
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "+${FormatUtil.formatNumber(uiState.auraPerSec)}/с",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = ElectricPurple
                    )
                    Text(
                        text = "Тап: +${FormatUtil.formatNumber(uiState.clickPower)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

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
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (uiState.isKochModeActive) "🔥 РЕЖИМ КОЧАЛКИ (${uiState.kochModeRemainingSec}с)" else "Энергия Кочалки (Адреналин)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isKochModeActive) BonesmashCrimson else TextSecondary
                    )
                }

                if (uiState.currentCombo > 1) {
                    Text(
                        text = "COMBO x${uiState.currentCombo}! (${String.format(java.util.Locale.US, "%.1f", uiState.comboMultiplier)}x)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            val progress = (uiState.kochAdrenaline / 100f).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { if (uiState.isKochModeActive) 1f else progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (uiState.isKochModeActive) BonesmashCrimson else PureGold,
                trackColor = DarkCardBorder
            )
        }
    }
}

@Composable
private fun BrawlCurrencyCapsule(icon: String, value: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1E2235))
            .border(1.dp, color.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 11.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = value,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
        }
    }
}
