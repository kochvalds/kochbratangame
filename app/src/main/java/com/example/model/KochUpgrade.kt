package com.example.model

import kotlin.math.pow
import kotlin.math.roundToLong

data class KochUpgradeDef(
    val id: Int,
    val nameRu: String,
    val description: String,
    val baseCost: Double,
    val multiplierBonus: Double, // e.g. +0.25 to overall multiplier
    val costMultiplier: Double = 1.35,
    val iconName: String
)

data class KochUpgradeItem(
    val def: KochUpgradeDef,
    val currentLevel: Int,
    val currentCost: Double,
    val bonusProvided: Double,
    val canAfford: Boolean
)

object KochUpgradesCatalog {
    val UPGRADES = listOf(
        KochUpgradeDef(
            id = 1,
            nameRu = "Турники и Брусья во дворе",
            description = "Широчайшие спины расширяют силуэт, V-образная фигура (+25% к множителю клика).",
            baseCost = 500.0,
            multiplierBonus = 0.25,
            iconName = "pullup"
        ),
        KochUpgradeDef(
            id = 2,
            nameRu = "Креатиновый заряд & Протеин",
            description = "Мышцы заливает водой и силой, взрывной памп (+50% к ауре в секунду).",
            baseCost = 3000.0,
            multiplierBonus = 0.50,
            iconName = "creatine"
        ),
        KochUpgradeDef(
            id = 3,
            nameRu = "Жим лёжа 140кг на раз",
            description = "Фоидки разбегаются при звуке падающей штанги (+100% к силе мога).",
            baseCost = 25000.0,
            multiplierBonus = 1.00,
            iconName = "bench"
        ),
        KochUpgradeDef(
            id = 4,
            nameRu = "Дух Братана (Kochvalds Legacy)",
            description = "Несокрушимая энергия кочалки. Увеличивает длительность Режима Кочалки.",
            baseCost = 200000.0,
            multiplierBonus = 2.00,
            iconName = "spirit"
        )
    )

    fun calculateCost(def: KochUpgradeDef, level: Int): Double {
        return (def.baseCost * def.costMultiplier.pow(level.toDouble())).roundToLong().toDouble()
    }
}
