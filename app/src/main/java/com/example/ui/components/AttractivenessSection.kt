package com.example.ui.components

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.model.PassiveUpgradeItem
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.FormatUtil
import com.example.viewmodel.GameUiState

@Composable
fun AttractivenessSection(
    uiState: GameUiState,
    onBuyPassiveUpgrade: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Attractiveness Automation Overview Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "✨ УРОВЕНЬ ПРИВЛЕКАТЕЛЬНОСТИ (АВТОМАТИЗАЦИЯ)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = ElectricPurple
                            )
                            Text(
                                text = "Пассивный моггинг окружающих каждую секунду",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "ПАССИВНАЯ АУРА",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted
                                )
                                Text(
                                    text = "+${FormatUtil.formatNumber(uiState.auraPerSec)} / сек",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ElectricPurple
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "ТЕКУЩИЙ PSL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted
                                )
                                Text(
                                    text = "${uiState.currentEvolution.pslScore} / 10.0",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "ПАССИВНЫЕ ЛУКСМАКС-ПРИВЫЧКИ (АВТО-МОГГИНГ)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        // Upgrades list
        items(uiState.passiveUpgrades, key = { it.def.id }) { item ->
            PassiveUpgradeCard(
                item = item,
                onBuy = { onBuyPassiveUpgrade(item.def.id) }
            )
        }
    }
}

@Composable
private fun PassiveUpgradeCard(
    item: PassiveUpgradeItem,
    onBuy: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (item.canAfford) ElectricPurple.copy(alpha = 0.5f) else DarkCardBorder
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
            // Icon & Description
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
                                    ElectricPurple.copy(alpha = 0.25f),
                                    DarkSurfaceVariant
                                )
                            )
                        )
                        .border(1.dp, ElectricPurple.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val iconSymbol = when (item.def.iconName) {
                        "ice" -> "🧊"
                        "spf" -> "🧴"
                        "eyes" -> "👁️"
                        "cantus" -> "🦅"
                        "sleep" -> "💤"
                        "sigma" -> "🕶️"
                        else -> "👑"
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
                                color = ElectricPurple
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
                        text = "+${FormatUtil.formatNumber(item.def.baseAuraPerSec)} / сек (Всего: +${FormatUtil.formatNumber(item.auraPerSecProvided)}/с)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PureGold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Buy Button
            Button(
                onClick = onBuy,
                enabled = item.canAfford,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricPurple,
                    contentColor = Color.White,
                    disabledContainerColor = DarkCardBorder,
                    disabledContentColor = TextMuted
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                modifier = Modifier
                    .height(44.dp)
                    .testTag("buy_passive_upgrade_${item.def.id}")
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ПРОКАЧАТЬ",
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
