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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Speed
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarAsset
import com.example.model.CryptoCoin
import com.example.model.LifestyleCatalog
import com.example.model.RealEstateAsset
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

enum class LifestyleTab(val title: String, val iconEmoji: String) {
    CARS("Авто & Номера", "🏎️"),
    REAL_ESTATE("Недвижка", "🏰"),
    CRYPTO("Криптовалюты", "🪙")
}

@Composable
fun LifestyleSection(
    uiState: GameUiState,
    onBuyCar: (String) -> Unit,
    onEquipCar: (String) -> Unit,
    onBuyRealEstate: (String) -> Unit,
    onBuyCrypto: (String, Double) -> Unit,
    onSellCrypto: (String, Double) -> Unit,
    onSpinPlate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(LifestyleTab.CARS) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Tab Selector Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LifestyleTab.values().forEach { tab ->
                    val isSelected = selectedTab == tab
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTab = tab },
                        label = {
                            Text(
                                text = "${tab.iconEmoji} ${tab.title}",
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PureGold,
                            selectedLabelColor = Color.Black,
                            containerColor = DarkSurface,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = DarkCardBorder,
                            selectedBorderColor = PureGold
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        when (selectedTab) {
            LifestyleTab.CARS -> {
                // Russian Plate Gacha Roulette Card
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            2.dp,
                            Brush.horizontalGradient(listOf(Color(0xFFD500F9), PureGold, NeonCyan))
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "🔢", fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "ГОСНОМЕРА РФ (ГАЧА-РУЛЕТКА)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = PureGold,
                                        letterSpacing = 1.sp
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(uiState.equippedPlate.rarity.colorHex).copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = uiState.equippedPlate.rarity.titleRu.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(uiState.equippedPlate.rarity.colorHex)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Realistic Russian License Plate
                            RussianPlateView(plate = uiState.equippedPlate)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = uiState.equippedPlate.specialName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Бафф номера: +${(uiState.equippedPlate.clickBonus * 100).toInt()}% клик, +${(uiState.equippedPlate.passiveBonus * 100).toInt()}% пассив",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = NeonCyan
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = onSpinPlate,
                                enabled = uiState.auraPoints >= uiState.plateSpinCost,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PureGold,
                                    disabledContainerColor = DarkSurfaceVariant
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("spin_plate_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Casino,
                                        contentDescription = "Крутить",
                                        tint = Color.Black,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "ВЫБИТЬ БЛАТНОЙ НОМЕР (${FormatUtil.formatNumber(uiState.plateSpinCost)} АУРЫ)",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "АВТОПАРК ГИГАЧАДА:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )
                }

                items(LifestyleCatalog.CARS, key = { it.id }) { car ->
                    val isOwned = uiState.ownedCarIds.contains(car.id)
                    val isEquipped = uiState.equippedCarId == car.id

                    CarCard(
                        car = car,
                        isOwned = isOwned,
                        isEquipped = isEquipped,
                        canAfford = uiState.auraPoints >= car.costAura,
                        onBuy = { onBuyCar(car.id) },
                        onEquip = { onEquipCar(car.id) }
                    )
                }
            }

            LifestyleTab.REAL_ESTATE -> {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PureGold.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏰", fontSize = 32.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ИМПЕРИЯ НЕДВИЖИМОСТИ",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PureGold
                                )
                                Text(
                                    text = "Каждая купленная недвижимость приносит пассивную аренду в ауре каждую секунду!",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

                items(LifestyleCatalog.REAL_ESTATE, key = { it.id }) { estate ->
                    val isOwned = uiState.ownedRealEstateIds.contains(estate.id)
                    RealEstateCard(
                        estate = estate,
                        isOwned = isOwned,
                        canAfford = uiState.auraPoints >= estate.costAura,
                        onBuy = { onBuyRealEstate(estate.id) }
                    )
                }
            }

            LifestyleTab.CRYPTO -> {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurface),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "📈", fontSize = 32.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "КРИПТО-ПОРТФЕЛЬ БРАТАНА",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = NeonCyan
                                )
                                Text(
                                    text = "Крипта колеблется в реальном времени! Покупай на низах, фиксируй прибыль в ауре!",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

                items(uiState.cryptoCoins, key = { it.symbol }) { coin ->
                    CryptoCard(
                        coin = coin,
                        userAura = uiState.auraPoints,
                        onBuy = { amountAura -> onBuyCrypto(coin.symbol, amountAura) },
                        onSell = { amountCoins -> onSellCrypto(coin.symbol, amountCoins) }
                    )
                }
            }
        }
    }
}

@Composable
fun CarCard(
    car: CarAsset,
    isOwned: Boolean,
    isEquipped: Boolean,
    canAfford: Boolean,
    onBuy: () -> Unit,
    onEquip: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isEquipped) DarkSurfaceVariant else DarkSurface
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isEquipped) PureGold else if (isOwned) NeonCyan.copy(alpha = 0.5f) else DarkCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
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
                    Text(text = car.iconEmoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = car.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = "${car.brand} • ${car.horsepower} л.с. • ${car.speedKmh} км/ч",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                if (isEquipped) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PureGold.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "В ГАРАЖЕ 👑",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = PureGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = car.description,
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "⚡ +${(car.clickMultiplierBonus * 100).toInt()}% клик",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureGold
                    )
                    Text(
                        text = "✨ +${(car.passiveMultiplierBonus * 100).toInt()}% пассив",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricPurple
                    )
                }

                if (!isOwned) {
                    Button(
                        onClick = onBuy,
                        enabled = canAfford,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            disabledContainerColor = DarkSurfaceVariant
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "${FormatUtil.formatNumber(car.costAura)} ауры",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                } else if (!isEquipped) {
                    Button(
                        onClick = onEquip,
                        colors = ButtonDefaults.buttonColors(containerColor = PureGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Выбрать тачку",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RealEstateCard(
    estate: RealEstateAsset,
    isOwned: Boolean,
    canAfford: Boolean,
    onBuy: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isOwned) PureGold else DarkCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
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
                    Text(text = estate.iconEmoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = estate.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = estate.location,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                if (isOwned) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PureGold.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "В СОБСТВЕННОСТИ ✓",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = PureGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = estate.description,
                fontSize = 11.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Доход: +${FormatUtil.formatNumber(estate.passiveAuraPerSec)} ауры/сек",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricPurple
                )

                if (!isOwned) {
                    Button(
                        onClick = onBuy,
                        enabled = canAfford,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PureGold,
                            disabledContainerColor = DarkSurfaceVariant
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Купить: ${FormatUtil.formatNumber(estate.costAura)}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoCard(
    coin: CryptoCoin,
    userAura: Double,
    onBuy: (Double) -> Unit,
    onSell: (Double) -> Unit
) {
    val isPositive = coin.change24hPercent >= 0
    val changeColor = if (isPositive) Color(0xFF00E676) else BonesmashCrimson

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = coin.iconEmoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${coin.name} (${coin.symbol})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = "В кошельке: ${String.format("%.4f", coin.ownedAmount)} ${coin.symbol}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NeonCyan
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${FormatUtil.formatNumber(coin.currentPriceAura)} ауры",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = PureGold
                    )
                    Text(
                        text = "${if (isPositive) "+" else ""}${String.format("%.1f", coin.change24hPercent)}%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = changeColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val buyAura = 1000.0.coerceAtMost(userAura)
                Button(
                    onClick = { onBuy(buyAura) },
                    enabled = userAura >= 100.0,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "КУПИТЬ", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.Black)
                }

                Button(
                    onClick = { onSell(coin.ownedAmount) },
                    enabled = coin.ownedAmount > 0.0001,
                    colors = ButtonDefaults.buttonColors(containerColor = BonesmashCrimson),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "ПРОДАТЬ ВСЁ", fontSize = 11.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        }
    }
}
