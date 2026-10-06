package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.model.BoxReward
import com.example.model.BoxType
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.FormatUtil
import com.example.viewmodel.GameUiState

@Composable
fun BoxesSection(
    uiState: GameUiState,
    onOpenBox: (BoxType) -> Unit,
    onDismissReward: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Brawl Boxes Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Color(0xFFE040FB), NeonCyan))),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "🎁 Лутбоксы Луксмакса (Brawl Style)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PureGold
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Открывай Бро-Боксы за жетоны кликов или мегаящики за кристаллы! Дропай горы ауры, гемы и редкие банные веники!",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🎟️ ${uiState.tokens} Жетонов", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("💎 ${uiState.gems} Гемов", color = Color(0xFF00E5FF), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("📦 ${uiState.openedBoxesCount} Открыто", color = PureGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // 3 Box Cards
        items(BoxType.values()) { box ->
            BoxCard(
                box = box,
                uiState = uiState,
                onOpen = { onOpenBox(box) }
            )
        }
    }

    // Interactive Open Reward Dialog
    if (uiState.showBoxRewardDialog && uiState.currentBoxRewards.isNotEmpty()) {
        BoxRewardDialog(
            rewards = uiState.currentBoxRewards,
            boxType = uiState.currentOpeningBoxType,
            onDismiss = onDismissReward
        )
    }
}

@Composable
private fun BoxCard(
    box: BoxType,
    uiState: GameUiState,
    onOpen: () -> Unit
) {
    val canAffordTokens = box.tokenCost > 0 && uiState.tokens >= box.tokenCost
    val canAffordGems = box.gemCost > 0 && uiState.gems >= box.gemCost
    val canAffordAura = uiState.auraPoints >= box.auraCost
    val canOpen = canAffordTokens || canAffordGems || canAffordAura

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("box_card_${box.id}"),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Color(box.colorHex), Color(box.colorHex).copy(alpha = 0.5f)))),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(box.colorHex).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(box.iconEmoji, fontSize = 32.sp)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = box.titleRu,
                    color = Color(box.colorHex),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = box.description,
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 15.sp
                )
                Spacer(Modifier.height(4.dp))
                val priceText = when {
                    box.tokenCost > 0 -> "${box.tokenCost} 🎟️ или ${FormatUtil.formatNumber(box.auraCost)} ⚡"
                    box.gemCost > 0 -> "${box.gemCost} 💎 или ${FormatUtil.formatNumber(box.auraCost)} ⚡"
                    else -> "${FormatUtil.formatNumber(box.auraCost)} ⚡"
                }
                Text(priceText, color = PureGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = onOpen,
                enabled = canOpen,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(box.colorHex),
                    disabledContainerColor = DarkSurface.copy(alpha = 0.6f)
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (canOpen) "ОТКРЫТЬ" else "МАЛО",
                    fontWeight = FontWeight.Bold,
                    color = if (canOpen) Color.Black else TextMuted,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun BoxRewardDialog(
    rewards: List<BoxReward>,
    boxType: BoxType?,
    onDismiss: () -> Unit
) {
    var revealedIndex by remember { mutableStateOf(0) }
    val remaining = (rewards.size - revealedIndex).coerceAtLeast(0)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable {
                    if (revealedIndex < rewards.size) {
                        revealedIndex++
                    } else {
                        onDismiss()
                    }
                },
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(PureGold, Color(0xFFE040FB))))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${boxType?.iconEmoji ?: "🎁"} ${boxType?.titleRu ?: "ЯЩИК"}",
                    color = PureGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(10.dp))

                val currentReward = rewards.getOrNull(revealedIndex.coerceAtMost(rewards.lastIndex))

                if (currentReward?.unlockedCharacter != null) {
                    val newChar = currentReward.unlockedCharacter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PureGold)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "🎉 НОВЫЙ БОЕЦ РАЗБЛОКИРОВАН! 🎉",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    CharacterArtCard(
                        character = newChar,
                        size = 110.dp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = newChar.name,
                        color = Color(newChar.rarity.colorHex),
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "${newChar.rarity.titleRu} • ${newChar.title}",
                        color = PureGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = newChar.quote,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE040FB).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        when (currentReward?.iconEmoji) {
                            "⚡" -> GoldAuraIcon(size = 46.dp)
                            "💎" -> GemDiamondIcon(size = 46.dp)
                            "🎟️" -> BrawlTicketIcon(size = 46.dp)
                            "🌿" -> BanyaBroomIcon(size = 46.dp)
                            else -> Text(currentReward?.iconEmoji ?: "✨", fontSize = 42.sp)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (currentReward != null) {
                        Text(
                            text = currentReward.title,
                            color = Color(currentReward.colorHex),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = currentReward.amountText,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFFF9100).copy(alpha = 0.2f))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (remaining > 1) "ОСТАЛОСЬ ПРЕДМЕТОВ: $remaining (ТАПНИ!)" else "ПОСЛЕДНИЙ ПРЕДМЕТ! (ТАПНИ ДЛЯ ВЫХОДА)",
                        color = PureGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (revealedIndex < rewards.size) {
                            revealedIndex++
                        } else {
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PureGold)
                ) {
                    Text(
                        text = if (remaining > 0) "СЛЕДУЮЩИЙ ДРОП ➡️" else "ЗАБРАТЬ ВСЁ! 🎉",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
