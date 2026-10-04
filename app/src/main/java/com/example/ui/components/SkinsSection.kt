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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CharacterDef
import com.example.model.CharacterSkinCatalog
import com.example.model.SkinDef
import com.example.model.SkinItem
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
fun SkinsSection(
    uiState: GameUiState,
    skins: List<SkinItem>,
    onSelectSkin: (String) -> Unit,
    onBuySkin: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCharId by remember { mutableStateOf("kochvalds") }
    val characters = CharacterSkinCatalog.CHARACTERS
    val currentChar = characters.find { it.id == selectedCharId } ?: characters.first()
    val charSkins = skins.filter { it.def.characterId == selectedCharId }
    val activeSkin = skins.find { it.isSelected }?.def ?: CharacterSkinCatalog.SKINS.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Active Skin Hero Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(
                    2.dp,
                    Brush.horizontalGradient(listOf(NeonCyan, ElectricPurple, PureGold))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .border(2.dp, PureGold, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = activeSkin.drawableRes),
                            contentDescription = activeSkin.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "АКТИВНЫЙ СКИН:",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PureGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = activeSkin.badgeText, fontSize = 9.sp, fontWeight = FontWeight.Black, color = PureGold)
                            }
                        }

                        Text(
                            text = activeSkin.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonCyan
                        )

                        Text(
                            text = "Бонусы: +${(activeSkin.clickMultiplierBonus * 100).toInt()}% клик, +${(activeSkin.passiveMultiplierBonus * 100).toInt()}% пассивка",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // Character Selector Chips
        item {
            Text(
                text = "ВЫБЕРИ ПЕРСОНАЖА:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TextSecondary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(characters, key = { it.id }) { char ->
                    val isSelected = char.id == selectedCharId
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCharId = char.id },
                        label = {
                            Text(
                                text = char.name,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Text(
                                text = when (char.id) {
                                    "kochvalds" -> "💪"
                                    "vlados" -> "👔"
                                    "temshik" -> "📱"
                                    "prof_mew" -> "📐"
                                    else -> "🗿"
                                },
                                fontSize = 14.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonCyan,
                            selectedLabelColor = Color.Black,
                            containerColor = DarkSurface,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = DarkCardBorder,
                            selectedBorderColor = NeonCyan
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Character Quote Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkSurfaceVariant)
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = currentChar.title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureGold
                    )
                    Text(
                        text = currentChar.quote,
                        fontSize = 12.sp,
                        color = TextPrimary,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        // Skin Cards
        item {
            Text(
                text = "СКИНЫ ПЕРСОНАЖА (${currentChar.name.uppercase()}):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
        }

        items(charSkins, key = { it.def.id }) { skinItem ->
            SkinCard(
                skinItem = skinItem,
                canAfford = uiState.auraPoints >= skinItem.def.costAura,
                currentStage = uiState.evolutionStage,
                onSelect = { onSelectSkin(skinItem.def.id) },
                onBuy = { onBuySkin(skinItem.def.id) }
            )
        }
    }
}

@Composable
private fun SkinCard(
    skinItem: SkinItem,
    canAfford: Boolean,
    currentStage: Int,
    onSelect: () -> Unit,
    onBuy: () -> Unit
) {
    val def = skinItem.def
    val isStageMet = currentStage >= def.reqStage

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (skinItem.isSelected) DarkSurfaceVariant else DarkSurface
        ),
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(
            if (skinItem.isSelected) 2.dp else 1.dp,
            if (skinItem.isSelected) NeonCyan else DarkCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        1.5.dp,
                        if (skinItem.isSelected) NeonCyan else if (skinItem.isUnlocked) PureGold else DarkCardBorder,
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = def.drawableRes),
                    contentDescription = def.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (!skinItem.isUnlocked) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.55f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Заблокировано",
                            tint = TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = def.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = if (skinItem.isSelected) NeonCyan else TextPrimary
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (skinItem.isSelected) NeonCyan.copy(alpha = 0.2f) else DarkSurfaceVariant
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = def.badgeText,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (skinItem.isSelected) NeonCyan else PureGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = def.description,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Buffs row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (def.clickMultiplierBonus > 0) {
                        Text(
                            text = "⚡ +${(def.clickMultiplierBonus * 100).toInt()}% клик",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureGold
                        )
                    }
                    if (def.passiveMultiplierBonus > 0) {
                        Text(
                            text = "✨ +${(def.passiveMultiplierBonus * 100).toInt()}% пассив",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricPurple
                        )
                    }
                    if (def.adrenalineBonus > 0) {
                        Text(
                            text = "🔥 +${(def.adrenalineBonus * 100).toInt()}% кочалка",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BonesmashCrimson
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Button
                if (skinItem.isSelected) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(NeonCyan.copy(alpha = 0.2f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ВЫБРАН",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = NeonCyan
                            )
                        }
                    }
                } else if (skinItem.isUnlocked) {
                    Button(
                        onClick = onSelect,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "ВЫБРАТЬ", fontSize = 11.sp, fontWeight = FontWeight.Black)
                    }
                } else {
                    // Locked / Needs Purchase
                    if (!isStageMet) {
                        Text(
                            text = "🔒 Требуется Стадия Эволюции: Stage ${def.reqStage + 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5252)
                        )
                    } else {
                        Button(
                            onClick = onBuy,
                            enabled = canAfford,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PureGold,
                                contentColor = Color.Black,
                                disabledContainerColor = DarkSurfaceVariant,
                                disabledContentColor = TextMuted
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                text = "КУПИТЬ (${FormatUtil.formatNumber(def.costAura)} ✨)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
