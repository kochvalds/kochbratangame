package com.example.model

import kotlin.math.pow
import kotlin.math.roundToLong

data class PassiveUpgradeDef(
    val id: Int,
    val nameRu: String,
    val description: String,
    val baseCost: Double,
    val baseAuraPerSec: Double,
    val costMultiplier: Double = 1.25,
    val iconName: String
)

data class PassiveUpgradeItem(
    val def: PassiveUpgradeDef,
    val currentLevel: Int,
    val currentCost: Double,
    val auraPerSecProvided: Double,
    val canAfford: Boolean
)

object PassiveUpgradesCatalog {
    val UPGRADES = listOf(
        PassiveUpgradeDef(
            id = 1,
            nameRu = "Ледяное умывание & Гуаша",
            description = "Снимает отечность по утрам, повышает тонус кожи лица.",
            baseCost = 25.0,
            baseAuraPerSec = 1.0,
            costMultiplier = 1.22,
            iconName = "ice"
        ),
        PassiveUpgradeDef(
            id = 2,
            nameRu = "SPF 100 & Ретинол на ночь",
            description = "Защита коллагена и ровный фарфоровый тон лица.",
            baseCost = 150.0,
            baseAuraPerSec = 6.0,
            costMultiplier = 1.25,
            iconName = "spf"
        ),
        PassiveUpgradeDef(
            id = 3,
            nameRu = "Тренировка Хантер Айз (Hunter Eyes)",
            description = "Легкий прищур и тренировка круговой мышцы глаз.",
            baseCost = 800.0,
            baseAuraPerSec = 28.0,
            costMultiplier = 1.28,
            iconName = "eyes"
        ),
        PassiveUpgradeDef(
            id = 4,
            nameRu = "Положительный кантус",
            description = "Внешний уголок глаз выше внутреннего. Аура хищника.",
            baseCost = 4500.0,
            baseAuraPerSec = 110.0,
            costMultiplier = 1.30,
            iconName = "cantus"
        ),
        PassiveUpgradeDef(
            id = 5,
            nameRu = "Ночной Мьюинг (Навык сна)",
            description = "Челюсть на замке даже во время фазы глубокого сна.",
            baseCost = 25000.0,
            baseAuraPerSec = 450.0,
            costMultiplier = 1.32,
            iconName = "sleep"
        ),
        PassiveUpgradeDef(
            id = 6,
            nameRu = "Походка и Осанка Сигмы",
            description = "Расправленные плечи, медленный взгляд сверху вниз.",
            baseCost = 120000.0,
            baseAuraPerSec = 1800.0,
            costMultiplier = 1.35,
            iconName = "sigma"
        ),
        PassiveUpgradeDef(
            id = 7,
            nameRu = "Свита Моггеров",
            description = "Толпа братанов-моггеров идет позади тебя и могает всех подряд.",
            baseCost = 750000.0,
            baseAuraPerSec = 8000.0,
            costMultiplier = 1.40,
            iconName = "squad"
        )
    )

    fun calculateCost(def: PassiveUpgradeDef, level: Int): Double {
        return (def.baseCost * def.costMultiplier.pow(level.toDouble())).roundToLong().toDouble()
    }

    fun calculatePower(def: PassiveUpgradeDef, level: Int): Double {
        return def.baseAuraPerSec * level
    }
}
