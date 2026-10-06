package com.example.model

data class BanyaHybrid(
    val id: String,
    val name: String,
    val parentAId: String,
    val parentBId: String,
    val description: String,
    val iconEmoji: String,
    val clickMultiplierBonus: Double,
    val passiveMultiplierBonus: Double,
    val steamTempC: Int = 100
)

data class BoxReward(
    val title: String,
    val amountText: String,
    val iconEmoji: String,
    val colorHex: Long,
    val unlockedCharacter: CharacterDef? = null
)

enum class BoxType(
    val id: String,
    val titleRu: String,
    val description: String,
    val iconEmoji: String,
    val auraCost: Double,
    val tokenCost: Long,
    val gemCost: Int,
    val rewardsCount: Int,
    val colorHex: Long
) {
    BRAWL_BOX(
        id = "brawl_box",
        titleRu = "Бро-Бокс",
        description = "Классический бокс луксмаксера. 2 предмета, шанс выбить бойца 25%!",
        iconEmoji = "📦",
        auraCost = 15000.0,
        tokenCost = 100L,
        gemCost = 0,
        rewardsCount = 2,
        colorHex = 0xFF00E5FF
    ),
    BIG_BOX(
        id = "big_box",
        titleRu = "Большой Ящик",
        description = "В 3 раза больше наград! 4 предмета, повышенный шанс бойца 50%!",
        iconEmoji = "💼",
        auraCost = 60000.0,
        tokenCost = 0L,
        gemCost = 30,
        rewardsCount = 4,
        colorHex = 0xFFFF9100
    ),
    MEGA_BOX(
        id = "mega_box",
        titleRu = "МЕГАЯЩИК",
        description = "Легендарный дроп! 8 предметов, гигантский шанс выбить бойца 85%!",
        iconEmoji = "👑",
        auraCost = 250000.0,
        tokenCost = 0L,
        gemCost = 80,
        rewardsCount = 8,
        colorHex = 0xFFFFD600
    )
}
