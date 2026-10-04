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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Stars
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
import com.example.model.EvolutionStageInfo
import com.example.model.EvolutionStages
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
fun EvolutionProfileSection(
    uiState: GameUiState,
    onEvolve: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Current Profile Hero Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = uiState.activeSkin.drawableRes),
                            contentDescription = uiState.activeSkin.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Dark gradient
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            DarkSurface.copy(alpha = 0.7f),
                                            DarkSurface
                                        )
                                    )
                                )
                        )

                        // Evolution Stage Title inside banner
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "ЭВОЛЮЦИЯ: ${uiState.currentEvolution.nameRu}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = NeonCyan
                            )
                            Text(
                                text = "${uiState.currentEvolution.subtitleRu} • Множитель: x${uiState.currentEvolution.clickMultiplier.toInt()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureGold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = uiState.currentEvolution.description,
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.currentEvolution.memeQuote,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = ElectricPurple
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Next Evolution Action Box
                        val next = uiState.nextEvolution
                        if (next != null) {
                            val progress = (uiState.auraPoints / next.reqAura).toFloat().coerceIn(0f, 1f)
                            Text(
                                text = "СЛЕДУЮЩИЙ УРОВЕНЬ: ${next.nameRu}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = PureGold,
                                trackColor = DarkCardBorder
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${FormatUtil.formatNumber(uiState.auraPoints)} / ${FormatUtil.formatNumber(next.reqAura)} ауры",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                                Text(
                                    text = "${(progress * 100).toInt()}%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PureGold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = onEvolve,
                                enabled = uiState.canEvolve,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PureGold,
                                    contentColor = Color.Black,
                                    disabledContainerColor = DarkCardBorder,
                                    disabledContentColor = TextMuted
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("evolve_appearance_button")
                            ) {
                                Text(
                                    text = if (uiState.canEvolve) "✨ ЭВОЛЮЦИОНИРОВАТЬ В ${next.nameRu.uppercase()}!" else "ТРЕБУЕТСЯ ${FormatUtil.formatNumber(next.reqAura)} АУРЫ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(DarkSurfaceVariant)
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "👑 ДОСТИГНУТ МАКСИМАЛЬНЫЙ РАНГ: АПЕКС БОГ ЛУКСМАКСА",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Stats Summary
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "📊 СТАТИСТИКА ЛУКСМАКСЕРА",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StatRow(label = "Всего кликов по фоидке:", value = FormatUtil.formatNumberLong(uiState.totalMogs))
                    StatRow(label = "Ударов бонсмешинга:", value = FormatUtil.formatNumberLong(uiState.bonesmashHits))
                    StatRow(label = "Всего заработано ауры:", value = FormatUtil.formatNumber(uiState.totalAuraEarned))
                    StatRow(label = "Базовая сила клика:", value = "+${FormatUtil.formatNumber(uiState.clickPower)}")
                    StatRow(label = "Пассивный доход:", value = "+${FormatUtil.formatNumber(uiState.auraPerSec)}/с")
                }
            }
        }

        // Evolution Ladder
        item {
            Text(
                text = "ПУТЬ ЭВОЛЮЦИИ ПРОФИЛЯ (7 СТАДИЙ)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        items(EvolutionStages.STAGES, key = { it.stage }) { stage ->
            StageItemCard(
                stage = stage,
                isCurrent = stage.stage == uiState.evolutionStage,
                isUnlocked = stage.stage <= uiState.evolutionStage
            )
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = TextMuted)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
private fun StageItemCard(
    stage: EvolutionStageInfo,
    isCurrent: Boolean,
    isUnlocked: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) DarkSurfaceVariant else DarkSurface
        ),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCurrent) NeonCyan else if (isUnlocked) PureGold.copy(alpha = 0.4f) else DarkCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCurrent) NeonCyan.copy(alpha = 0.2f) else DarkCardBorder
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCurrent) {
                    Text(text = "👑", fontSize = 18.sp)
                } else if (isUnlocked) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PureGold,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stage.nameRu,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrent) NeonCyan else if (isUnlocked) TextPrimary else TextMuted
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PSL ${stage.pslScore}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureGold
                    )
                }
                Text(
                    text = stage.profileFeature,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Text(
                    text = "Буст: x${stage.clickMultiplier.toInt()} к клику, x${stage.passiveMultiplier.toInt()} к пассивке",
                    fontSize = 10.sp,
                    color = ElectricPurple
                )
            }
        }
    }
}
