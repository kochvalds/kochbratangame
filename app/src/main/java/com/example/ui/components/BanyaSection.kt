package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.BanyaHybrid
import com.example.model.CharacterDef
import com.example.model.CharacterSkinCatalog
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GameUiState

@Composable
fun BanyaSection(
    uiState: GameUiState,
    onFuse: (charAId: String, charBId: String) -> Unit,
    onDismissCelebration: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allChars = remember { CharacterSkinCatalog.CHARACTERS }
    var selectedA by remember { mutableStateOf(allChars.firstOrNull()?.id ?: "gleb_sportik") }
    var selectedB by remember { mutableStateOf(allChars.getOrNull(1)?.id ?: "zahar_baryga") }

    val infiniteTransition = rememberInfiniteTransition(label = "steam")
    val steamAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "steamAlpha"
    )

    val canAffordFusion = uiState.auraPoints >= 25000.0 && uiState.tokens >= 50L && selectedA != selectedB

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banya Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(PureGold, Color(0xFFFF6D00)))),
                shape = RoundedCornerShape(16.dp)
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
                        Text(
                            text = "🧖‍♂️ Русская Баня Луксмакса",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PureGold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFD84315).copy(alpha = steamAlpha))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "110°C 🔥 ПАР",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Скрещивай двух бойцов на раскаленной каменке под удары березового веника! Получай легендарные гибридные виды с запредельными множителями силы!",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("🌿 Дубовый веник", color = NeonCyan, fontSize = 12.sp)
                        Text("🪵 Каменка 110°C", color = Color(0xFFFF9100), fontSize = 12.sp)
                        Text("✨ ${uiState.banyaHybrids.size} открытых гибридов", color = PureGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Selection Character 1
        item {
            Text(
                text = "1️⃣ ВЫБЕРИ ПЕРВОГО БОЙЦА:",
                color = NeonCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(allChars) { char ->
                    val isSelected = char.id == selectedA
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedA = char.id },
                        label = { Text(char.name, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonCyan,
                            selectedLabelColor = Color.Black,
                            containerColor = DarkSurface,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) NeonCyan else DarkCardBorder
                        )
                    )
                }
            }
        }

        // Selection Character 2
        item {
            Text(
                text = "2️⃣ ВЫБЕРИ ВТОРОГО БОЙЦА:",
                color = PureGold,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(allChars) { char ->
                    val isSelected = char.id == selectedB
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedB = char.id },
                        label = { Text(char.name, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PureGold,
                            selectedLabelColor = Color.Black,
                            containerColor = DarkSurface,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) PureGold else DarkCardBorder
                        )
                    )
                }
            }
        }

        // Fusion Action Button
        item {
            val charAName = allChars.find { it.id == selectedA }?.name ?: selectedA
            val charBName = allChars.find { it.id == selectedB }?.name ?: selectedB

            Button(
                onClick = { onFuse(selectedA, selectedB) },
                enabled = canAffordFusion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("banya_fuse_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6D00),
                    disabledContainerColor = DarkSurface.copy(alpha = 0.5f)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "💨 ПОДДАТЬ ПАРКУ НА КАМЕНКУ! 🧖‍♂️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (canAffordFusion) Color.White else TextMuted
                    )
                    Text(
                        text = "Скрестить: $charAName + $charBName (25k ⚡ + 50 🎟️)",
                        fontSize = 11.sp,
                        color = if (canAffordFusion) PureGold else TextMuted
                    )
                }
            }
        }

        // Unlocked Hybrids Section
        item {
            Text(
                text = "🏆 ВАШИ БАННЫЕ ГИБРИДЫ (${uiState.banyaHybrids.size}):",
                color = PureGold,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        if (uiState.banyaHybrids.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "В бане пока никого не скрестили. Выберите двух персонажей выше и поддайте парку!",
                        color = TextMuted,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            items(uiState.banyaHybrids) { hybrid ->
                HybridCard(hybrid)
            }
        }
    }

    // Celebration Dialog for New Hybrid
    if (uiState.showBanyaCelebration && uiState.latestBanyaHybrid != null) {
        val hybrid = uiState.latestBanyaHybrid
        Dialog(onDismissRequest = onDismissCelebration) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(PureGold, Color(0xFFFF6D00))))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🧖‍♂️💨 ВЫКОВАН В БАНЕ!", color = PureGold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(10.dp))
                    Text(hybrid.name, color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(6.dp))
                    Text(hybrid.description, color = TextSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("+${(hybrid.clickMultiplierBonus * 100).toInt()}%", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Клик Моггинг", color = TextMuted, fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("+${(hybrid.passiveMultiplierBonus * 100).toInt()}%", color = PureGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Пассив Аура", color = TextMuted, fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${hybrid.steamTempC}°C", color = Color(0xFFFF5722), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Температура", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    Button(
                        onClick = onDismissCelebration,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PureGold)
                    ) {
                        Text("ЗАБРАТЬ ГИБРИДА! 🏆", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun HybridCard(hybrid: BanyaHybrid) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(PureGold.copy(alpha = 0.6f), Color(0xFFFF6D00).copy(alpha = 0.6f)))),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE65100).copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(hybrid.iconEmoji, fontSize = 24.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = hybrid.name,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = hybrid.description,
                    color = TextMuted,
                    fontSize = 11.sp,
                    lineHeight = 14.sp
                )
            }

            Spacer(Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text("+${(hybrid.clickMultiplierBonus * 100).toInt()}%", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text("+${(hybrid.passiveMultiplierBonus * 100).toInt()}%", color = PureGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}
