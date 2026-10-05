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
import com.example.model.CharacterSkinCatalog
import com.example.model.LifestyleCatalog
import com.example.model.RussianPlate
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

enum class InventoryCategory(val title: String, val iconEmoji: String) {
    ALL("Все предметы", "🎒"),
    PLATES("Госномера РФ", "🔢"),
    CARS("Автопарк", "🏎️"),
    ESTATES("Недвижимость", "🏰"),
    HYBRIDS("Банные гибриды", "🧖‍♂️"),
    SKINS("Скины и бойцы", "🎭"),
    CRYPTO("Крипто-кошелек", "🪙")
}

@Composable
fun InventorySection(
    uiState: GameUiState,
    onEquipPlate: (String) -> Unit,
    onEquipCar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(InventoryCategory.ALL) }

    val ownedCars = remember(uiState.ownedCarIds) {
        LifestyleCatalog.CARS.filter { uiState.ownedCarIds.contains(it.id) }
    }
    val ownedEstates = remember(uiState.ownedRealEstateIds) {
        LifestyleCatalog.REAL_ESTATE.filter { uiState.ownedRealEstateIds.contains(it.id) }
    }
    val unlockedSkins = remember(uiState.skins) {
        uiState.skins.filter { it.isUnlocked }
    }
    val cryptoValueAura = remember(uiState.cryptoCoins) {
        uiState.cryptoCoins.sumOf { it.ownedAmount * it.currentPriceAura }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Brawl Stars Style Header Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, PureGold),
                modifier = Modifier.fillMaxWidth().testTag("inventory_header_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎒", fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ЛИЧНЫЙ ИНВЕНТАРЬ БОЙЦА",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                                Text(
                                    text = "Все накопленные трофеи, блатные номера, тачки и активы",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Stats Quick Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceVariant)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        InventoryStatBadge("🔢 Номера", "${uiState.ownedPlates.size}")
                        InventoryStatBadge("🏎️ Тачки", "${ownedCars.size}")
                        InventoryStatBadge("🏰 Недвижка", "${ownedEstates.size}")
                        InventoryStatBadge("🧖‍♂️ Гибриды", "${uiState.banyaHybrids.size}")
                        InventoryStatBadge("🎭 Скины", "${unlockedSkins.size}")
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(InventoryCategory.values()) { category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) PureGold else DarkSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) PureGold else DarkCardBorder,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "${category.iconEmoji} ${category.title}",
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.Black else TextPrimary
                        )
                    }
                }
            }
        }

        // SECTION: RUSSIAN PLATES
        if (selectedCategory == InventoryCategory.ALL || selectedCategory == InventoryCategory.PLATES) {
            item {
                InventorySectionHeader("КОЛЛЕКЦИЯ ГОСНОМЕРОВ РФ (${uiState.ownedPlates.size})", "🔢")
            }

            if (uiState.ownedPlates.isEmpty()) {
                item {
                    EmptyInventoryItem("У вас пока нет номеров. Крутите рулетку в Люкс-разделе!")
                }
            } else {
                items(uiState.ownedPlates, key = { it.fullPlate }) { plate ->
                    val isEquipped = uiState.equippedPlate.fullPlate == plate.fullPlate
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            if (isEquipped) 2.dp else 1.dp,
                            if (isEquipped) PureGold else DarkCardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RussianPlateView(plate = plate)

                                if (isEquipped) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(PureGold.copy(alpha = 0.2f))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "УСТАНОВЛЕН ✅",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Black,
                                            color = PureGold
                                        )
                                    }
                                } else {
                                    Button(
                                        onClick = { onEquipPlate(plate.fullPlate) },
                                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "ПОСТАВИТЬ",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${plate.rarity.titleRu} • ${plate.specialName}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(plate.rarity.colorHex)
                                )

                                Text(
                                    text = "⚡ +${(plate.clickBonus * 100).toInt()}% клик | ✨ +${(plate.passiveBonus * 100).toInt()}% пассив",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION: CARS
        if (selectedCategory == InventoryCategory.ALL || selectedCategory == InventoryCategory.CARS) {
            item {
                InventorySectionHeader("ГАРАЖ БОЙЦА (${ownedCars.size} из ${LifestyleCatalog.CARS.size})", "🏎️")
            }

            if (ownedCars.isEmpty()) {
                item {
                    EmptyInventoryItem("Гараж пуст! Загляните в салон автомобилей.")
                }
            } else {
                items(ownedCars, key = { it.id }) { car ->
                    val isEquipped = uiState.equippedCarId == car.id
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            if (isEquipped) 2.dp else 1.dp,
                            if (isEquipped) PureGold else DarkCardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Text(text = car.iconEmoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = car.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${car.brand} • ${car.horsepower} л.с. • +${(car.clickMultiplierBonus * 100).toInt()}% к тапу",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            if (isEquipped) {
                                Text(
                                    text = "ВЫБРАНА 👑",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                            } else {
                                Button(
                                    onClick = { onEquipCar(car.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = PureGold),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "ВЫБРАТЬ",
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

        // SECTION: REAL ESTATE
        if (selectedCategory == InventoryCategory.ALL || selectedCategory == InventoryCategory.ESTATES) {
            item {
                InventorySectionHeader("НЕДВИЖИМОСТЬ В СОБСТВЕННОСТИ (${ownedEstates.size})", "🏰")
            }

            if (ownedEstates.isEmpty()) {
                item {
                    EmptyInventoryItem("Недвижимость не приобретена. Покупайте апартаменты и виллы!")
                }
            } else {
                items(ownedEstates, key = { it.id }) { estate ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = estate.iconEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = estate.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                                Text(
                                    text = "${estate.location} • Доход: +${FormatUtil.formatNumber(estate.passiveAuraPerSec)} ауры/сек",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION: BANYA HYBRIDS
        if (selectedCategory == InventoryCategory.ALL || selectedCategory == InventoryCategory.HYBRIDS) {
            item {
                InventorySectionHeader("ВЫКОВАННЫЕ БАННЫЕ ГИБРИДЫ (${uiState.banyaHybrids.size})", "🧖‍♂️")
            }

            if (uiState.banyaHybrids.isEmpty()) {
                item {
                    EmptyInventoryItem("В бане пока не создано гибридов. Скрещивайте бойцов на пару!")
                }
            } else {
                items(uiState.banyaHybrids, key = { it.id }) { hybrid ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BonesmashCrimson.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = hybrid.iconEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = hybrid.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BonesmashCrimson
                                )
                                Text(
                                    text = "Пар 110°C • +${(hybrid.clickMultiplierBonus * 100).toInt()}% к силе всех тапов",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION: CRYPTO PORTFOLIO
        if (selectedCategory == InventoryCategory.ALL || selectedCategory == InventoryCategory.CRYPTO) {
            item {
                InventorySectionHeader("БАЛАНС КРИПТОВАЛЮТ (Оценка: ${FormatUtil.formatNumber(cryptoValueAura)} ⚡)", "🪙")
            }

            items(uiState.cryptoCoins, key = { it.symbol }) { coin ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = coin.iconEmoji, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "${coin.name} (${coin.symbol})",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Курс: ${FormatUtil.formatNumber(coin.currentPriceAura)} ауры",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${String.format("%.4f", coin.ownedAmount)} ${coin.symbol}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = if (coin.ownedAmount > 0) NeonCyan else TextMuted
                            )
                            Text(
                                text = "≈ ${FormatUtil.formatNumber(coin.ownedAmount * coin.currentPriceAura)} ⚡",
                                fontSize = 10.sp,
                                color = PureGold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InventoryStatBadge(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 9.sp, color = TextMuted)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Black, color = NeonCyan)
    }
}

@Composable
private fun InventorySectionHeader(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = PureGold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun EmptyInventoryItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant.copy(alpha = 0.5f))
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}
