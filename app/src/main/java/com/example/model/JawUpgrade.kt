package com.example.model

import kotlin.math.pow
import kotlin.math.roundToLong

data class JawUpgradeDef(
    val id: Int,
    val nameRu: String,
    val description: String,
    val baseCost: Double,
    val baseClickPower: Double,
    val costMultiplier: Double = 1.25,
    val iconName: String
)

data class JawUpgradeItem(
    val def: JawUpgradeDef,
    val currentLevel: Int,
    val currentCost: Double,
    val clickPowerProvided: Double,
    val canAfford: Boolean
)

object JawUpgradesCatalog {
    val UPGRADES = listOf(
        JawUpgradeDef(
            id = 1,
            nameRu = "Базовый Мьюинг",
            description = "Прижатие языка к нёбу. Повышает силу клика.",
            baseCost = 15.0,
            baseClickPower = 1.0,
            costMultiplier = 1.22,
            iconName = "mewing"
        ),
        JawUpgradeDef(
            id = 2,
            nameRu = "Бонсмешинг молотком",
            description = "Микротрещины стимулируют утолщение кости челюсти.",
            baseCost = 100.0,
            baseClickPower = 5.0,
            costMultiplier = 1.25,
            iconName = "hammer"
        ),
        JawUpgradeDef(
            id = 3,
            nameRu = "Жевательный тренажер & Мастика",
            description = "Гипертрофия массетеров расширяет нижнюю треть лица.",
            baseCost = 500.0,
            baseClickPower = 18.0,
            costMultiplier = 1.28,
            iconName = "mastic"
        ),
        JawUpgradeDef(
            id = 4,
            nameRu = "Квадратный подбородок",
            description = "Выдвижение ментального выступа вперед для мужественности.",
            baseCost = 2500.0,
            baseClickPower = 70.0,
            costMultiplier = 1.30,
            iconName = "chin"
        ),
        JawUpgradeDef(
            id = 5,
            nameRu = "Острый гониальный угол (90°)",
            description = "Фоидки режут пальцы об твой угол челюсти.",
            baseCost = 15000.0,
            baseClickPower = 300.0,
            costMultiplier = 1.32,
            iconName = "angle"
        ),
        JawUpgradeDef(
            id = 6,
            nameRu = "Полые щеки (Hollow Cheeks)",
            description = "Удаление комков Биша и тени под скулами.",
            baseCost = 80000.0,
            baseClickPower = 1200.0,
            costMultiplier = 1.35,
            iconName = "cheeks"
        ),
        JawUpgradeDef(
            id = 7,
            nameRu = "Алмазная Челюсть Моггера",
            description = "Кости трансформированы в несокрушимый кристалл чистого Чада.",
            baseCost = 500000.0,
            baseClickPower = 5000.0,
            costMultiplier = 1.40,
            iconName = "diamond"
        )
    )

    fun calculateCost(def: JawUpgradeDef, level: Int): Double {
        return (def.baseCost * def.costMultiplier.pow(level.toDouble())).roundToLong().toDouble()
    }

    fun calculatePower(def: JawUpgradeDef, level: Int): Double {
        return def.baseClickPower * level
    }
}
