package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.KochUpgradeItem
import com.example.ui.theme.BonesmashCrimson
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.FormatUtil
import com.example.viewmodel.GameUiState

@Composable
fun KochGymSection(
    uiState: GameUiState,
    onActivateKoch: () -> Unit,
    onBuyKochUpgrade: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Gym Workout Hero Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (uiState.isKochModeActive) BonesmashCrimson else DarkCardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_koch_gym),
                            contentDescription = "Кочалка и тренировка братана",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            DarkSurface.copy(alpha = 0.85f),
                                            DarkSurface
                                        )
                                    )
                                )
                        )

                        // Title inside banner
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp)
                        ) {
                            Text(
                                text = "💪 КОЧАЛКА БРАТАНА (KOCH POWER)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = if (uiState.isKochModeActive) BonesmashCrimson else PureGold
                            )
                            Text(
                                text = "«Кочнись сам и помоги братану смогать фоидок»",
                                fontSize = 11.sp,
                                color = TextPrimary
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        // Big KOCH BUTTON
                        val isReady = uiState.kochAdrenaline >= 100f || uiState.isKochModeActive
                        Button(
                            onClick = onActivateKoch,
                            enabled = isReady && !uiState.isKochModeActive,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BonesmashCrimson,
                                contentColor = Color.White,
                                disabledContainerColor = DarkCardBorder,
                                disabledContentColor = TextMuted
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("activate_koch_mode_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Whatshot,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (uiState.isKochModeActive) {
                                        "🔥 РЕЖИМ КОЧАЛКИ АКТИВЕН (${uiState.kochModeRemainingSec}с) [x2.5]"
                                    } else {
                                        "КОЧНУТЬ БРАТАНА! (${uiState.kochAdrenaline.toInt()}% Заряд)"
                                    },
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress
                        LinearProgressIndicator(
                            progress = { if (uiState.isKochModeActive) 1f else uiState.kochAdrenaline / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = BonesmashCrimson,
                            trackColor = DarkCardBorder
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "💡 Заряжай адреналин быстрыми кликами по фоидке или бонсмешингом. При активации: +150% к клику и +100% к пассивной ауре!",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "АПГРЕЙДЫ КОЧАЛКИ & МНОЖИТЕЛИ СИЛЫ",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        // Koch Upgrades List
        items(uiState.kochUpgrades, key = { it.def.id }) { item ->
            KochUpgradeCard(
                item = item,
                onBuy = { onBuyKochUpgrade(item.def.id) }
            )
        }
    }
}

@Composable
private fun KochUpgradeCard(
    item: KochUpgradeItem,
    onBuy: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (item.canAfford) BonesmashCrimson.copy(alpha = 0.5f) else DarkCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    BonesmashCrimson.copy(alpha = 0.25f),
                                    DarkSurfaceVariant
                                )
                            )
                        )
                        .border(1.dp, BonesmashCrimson.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val iconSymbol = when (item.def.iconName) {
                        "pullup" -> "🤸"
                        "creatine" -> "⚡"
                        "bench" -> "🏋️"
                        else -> "🔥"
                    }
                    Text(text = iconSymbol, fontSize = 22.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.def.nameRu,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkSurfaceVariant)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Ур. ${item.currentLevel}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BonesmashCrimson
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.def.description,
                        fontSize = 11.sp,
                        color = TextMuted,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "+${(item.def.multiplierBonus * 100).toInt()}% буст (Текущий: +${(item.bonusProvided * 100).toInt()}%)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PureGold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onBuy,
                enabled = item.canAfford,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BonesmashCrimson,
                    contentColor = Color.White,
                    disabledContainerColor = DarkCardBorder,
                    disabledContentColor = TextMuted
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                modifier = Modifier
                    .height(44.dp)
                    .testTag("buy_koch_upgrade_${item.def.id}")
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "КОЧНУТЬ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = FormatUtil.formatNumber(item.currentCost),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
