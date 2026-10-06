package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CharacterDef
import com.example.model.CharacterSkinCatalog
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

enum class CharacterRosterFilter(val title: String) {
    ALL("Все бойцы"),
    KYRGYZ("Кыргыз Антон 🇰🇬"),
    HYBRIDS("Банные Гибриды 🔥"),
    SPORTIK("Глеб Спортик 🏋️‍♂️"),
    BARYGA("Захар Барыга 👟"),
    PENISOV("Пенисов 333 🚗")
}

@Composable
fun SkinsSection(
    uiState: GameUiState,
    skins: List<SkinItem>,
    onSelectSkin: (String) -> Unit,
    onBuySkin: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(CharacterRosterFilter.ALL) }
    var selectedCharId by remember { mutableStateOf("kyrgyz_anton") }

    val allCharacters = remember { CharacterSkinCatalog.CHARACTERS }
    val filteredCharacters = remember(selectedFilter) {
        when (selectedFilter) {
            CharacterRosterFilter.ALL -> allCharacters
            CharacterRosterFilter.KYRGYZ -> allCharacters.filter { it.id == "kyrgyz_anton" }
            CharacterRosterFilter.HYBRIDS -> allCharacters.filter { it.isHybrid }
            CharacterRosterFilter.SPORTIK -> allCharacters.filter { it.id == "gleb_sportik" }
            CharacterRosterFilter.BARYGA -> allCharacters.filter { it.id == "zahar_baryga" }
            CharacterRosterFilter.PENISOV -> allCharacters.filter { it.id == "penisov" }
        }
    }

    val currentChar = allCharacters.find { it.id == selectedCharId } ?: filteredCharacters.firstOrNull() ?: allCharacters.first()
    val charSkins = skins.filter { it.def.characterId == currentChar.id }
    val activeSkin = skins.find { it.isSelected }?.def ?: CharacterSkinCatalog.SKINS.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 85.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Active Fighter Showcase Card with Art
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(
                    2.dp,
                    Brush.horizontalGradient(listOf(NeonCyan, PureGold, ElectricPurple))
                ),
                modifier = Modifier.fillMaxWidth().testTag("active_fighter_banner")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val activeChar = allCharacters.find { it.id == activeSkin.characterId } ?: currentChar
                    CharacterArtCard(
                        character = activeChar,
                        skin = activeSkin,
                        size = 76.dp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "АКТИВНЫЙ БОЕЦ:",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PureGold.copy(alpha = 0.25f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = activeSkin.badgeText,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                            }
                        }

                        Text(
                            text = activeSkin.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonCyan
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "⚡ +${(activeSkin.clickMultiplierBonus * 100).toInt()}% клик  |  ✨ +${(activeSkin.passiveMultiplierBonus * 100).toInt()}% пассив",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureGold
                        )
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(CharacterRosterFilter.values()) { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) PureGold else DarkSurfaceVariant)
                            .border(1.dp, if (isSelected) PureGold else DarkCardBorder, RoundedCornerShape(10.dp))
                            .clickable {
                                selectedFilter = filter
                                val firstInFilter = when (filter) {
                                    CharacterRosterFilter.ALL -> allCharacters.firstOrNull()?.id
                                    CharacterRosterFilter.KYRGYZ -> "kyrgyz_anton"
                                    CharacterRosterFilter.HYBRIDS -> allCharacters.firstOrNull { it.isHybrid }?.id
                                    CharacterRosterFilter.SPORTIK -> "gleb_sportik"
                                    CharacterRosterFilter.BARYGA -> "zahar_baryga"
                                    CharacterRosterFilter.PENISOV -> "penisov"
                                }
                                if (firstInFilter != null) selectedCharId = firstInFilter
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.Black else TextPrimary
                        )
                    }
                }
            }
        }

        // Horizontal Brawlers Avatar Select Row
        item {
            Text(
                text = "ВЫБЕРИ БОЙЦА (${filteredCharacters.size}):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TextSecondary,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredCharacters, key = { it.id }) { char ->
                    val isSelected = char.id == currentChar.id
                    val borderColor = if (isSelected) PureGold else Color(char.rarity.colorHex).copy(alpha = 0.6f)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { selectedCharId = char.id }
                            .padding(2.dp)
                    ) {
                        CharacterArtCard(
                            character = char,
                            skin = char.skins.firstOrNull(),
                            size = 64.dp,
                            modifier = Modifier.border(if (isSelected) 3.dp else 1.5.dp, borderColor, RoundedCornerShape(14.dp))
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = char.name.split(" ").first().take(9),
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) PureGold else TextPrimary
                        )
                    }
                }
            }
        }

        // Current Character Bio Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(currentChar.rarity.colorHex)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CharacterArtCard(
                        character = currentChar,
                        skin = charSkins.firstOrNull()?.def,
                        size = 56.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentChar.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(currentChar.rarity.colorHex))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = currentChar.rarity.titleRu.uppercase(),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }
                        Text(
                            text = currentChar.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PureGold
                        )
                        Text(
                            text = currentChar.quote,
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // Skins List for Current Selected Character
        item {
            Text(
                text = "СКИНЫ БОЙЦА (${charSkins.size}):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        items(charSkins, key = { it.def.id }) { item ->
            val skin = item.def
            val isSelected = item.isSelected
            val isUnlocked = item.isUnlocked
            val canAfford = uiState.auraPoints >= skin.costAura

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) DarkSurfaceVariant else DarkSurface
                ),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(
                    if (isSelected) 2.dp else 1.dp,
                    if (isSelected) PureGold else DarkCardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CharacterArtCard(
                        character = currentChar,
                        skin = skin,
                        size = 68.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = skin.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isSelected) PureGold else TextPrimary
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PureGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(text = skin.badgeText, fontSize = 8.sp, fontWeight = FontWeight.Black, color = PureGold)
                            }
                        }

                        Text(
                            text = skin.description,
                            fontSize = 10.sp,
                            color = TextMuted,
                            lineHeight = 13.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "⚡ +${(skin.clickMultiplierBonus * 100).toInt()}% клик",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                            Text(
                                text = "✨ +${(skin.passiveMultiplierBonus * 100).toInt()}% пассив",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ElectricPurple
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isSelected) {
                        Button(
                            onClick = {},
                            enabled = false,
                            colors = ButtonDefaults.buttonColors(disabledContainerColor = PureGold.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = PureGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "В БОЮ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PureGold)
                        }
                    } else if (isUnlocked) {
                        Button(
                            onClick = { onSelectSkin(skin.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = PureGold),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "ВЫБРАТЬ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                        }
                    } else {
                        Button(
                            onClick = { onBuySkin(skin.id) },
                            enabled = canAfford,
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = FormatUtil.formatNumber(skin.costAura),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
