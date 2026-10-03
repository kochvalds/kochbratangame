package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

data class SkinDef(
    val id: String,
    val characterId: String,
    val name: String,
    val description: String,
    @DrawableRes val drawableRes: Int,
    val reqStage: Int,
    val costAura: Double,
    val clickMultiplierBonus: Double,
    val passiveMultiplierBonus: Double,
    val adrenalineBonus: Double,
    val badgeText: String
)

data class SkinItem(
    val def: SkinDef,
    val isUnlocked: Boolean,
    val isSelected: Boolean
)

data class CharacterDef(
    val id: String,
    val name: String,
    val title: String,
    val quote: String,
    val skins: List<SkinDef>
)

object CharacterSkinCatalog {
    val SKINS = listOf(
        // Vlados skins
        SkinDef(
            id = "vlados_default",
            characterId = "vlados",
            name = "Владос Классик",
            description = "Фирменная полосатая рубашка и загадочная улыбка. Базовый скин братана.",
            drawableRes = R.drawable.char_vlados_default,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.15, // +15% к клику
            passiveMultiplierBonus = 0.0,
            adrenalineBonus = 0.10,
            badgeText = "СТАРТОВЫЙ"
        ),
        SkinDef(
            id = "vlados_sigma",
            characterId = "vlados",
            name = "Владос Апекс Сигма",
            description = "Острые скулы, стильный пиджак и неоновая аура превосходства.",
            drawableRes = R.drawable.char_vlados_sigma,
            reqStage = 2,
            costAura = 15000.0,
            clickMultiplierBonus = 0.50, // +50% к клику
            passiveMultiplierBonus = 0.25,
            adrenalineBonus = 0.30,
            badgeText = "+50% КЛИК"
        ),

        // Kochvalds skins
        SkinDef(
            id = "kochvalds_default",
            characterId = "kochvalds",
            name = "Кочвалд Классик",
            description = "Поло с полосатым воротником и победная ухмылка. Готов кочнуть в любой момент.",
            drawableRes = R.drawable.char_kochvalds_default,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.10,
            passiveMultiplierBonus = 0.15,
            adrenalineBonus = 0.25,
            badgeText = "+25% КОЧАЛКА"
        ),
        SkinDef(
            id = "kochvalds_beast",
            characterId = "kochvalds",
            name = "Кочвалд Титан Зала",
            description = "Огромные трапеции, спортивная майка и раскаленный огненный адреналин.",
            drawableRes = R.drawable.char_kochvalds_beast,
            reqStage = 3,
            costAura = 50000.0,
            clickMultiplierBonus = 0.40,
            passiveMultiplierBonus = 0.50, // +50% к пассивке
            adrenalineBonus = 0.60,
            badgeText = "+50% ПАССИВКА"
        ),

        // Classic Voidoc rival
        SkinDef(
            id = "voidoc_classic",
            characterId = "voidoc",
            name = "Фоидка Дерзкий",
            description = "Классический соперник для отработки ударов моггинга и мьюинга.",
            drawableRes = R.drawable.img_voidoc_rival,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.05,
            passiveMultiplierBonus = 0.05,
            adrenalineBonus = 0.05,
            badgeText = "МЕМНЫЙ"
        ),
        SkinDef(
            id = "gigachad_legend",
            characterId = "voidoc",
            name = "Чад-Монумент",
            description = "Абсолютная челюсть, высеченная из камня и ауры.",
            drawableRes = R.drawable.img_hero_gigachad,
            reqStage = 4,
            costAura = 200000.0,
            clickMultiplierBonus = 0.75,
            passiveMultiplierBonus = 0.75,
            adrenalineBonus = 0.75,
            badgeText = "ЛЕГЕНДАРНЫЙ"
        )
    )

    val CHARACTERS = listOf(
        CharacterDef(
            id = "vlados",
            name = "Владос",
            title = "Мастер Полосатого Стиля & Мьюинга",
            quote = "«Челюсть не ждёт, братан. Прижми язык к нёбу и могай!» 🤫🧏",
            skins = SKINS.filter { it.characterId == "vlados" }
        ),
        CharacterDef(
            id = "kochvalds",
            name = "Кочвалд",
            title = "Легенда Кочалки & Адреналиновый Бог",
            quote = "«Либо ты кочаешь братана, либо фоидки могают тебя!» 💪🔥",
            skins = SKINS.filter { it.characterId == "kochvalds" }
        ),
        CharacterDef(
            id = "voidoc",
            name = "Фоидка & Чад",
            title = "Классический Соперник & Монумент",
            quote = "«Попробуй превзойти мой гониальный угол, если сможешь!» 🗿",
            skins = SKINS.filter { it.characterId == "voidoc" }
        )
    )

    fun getSkin(skinId: String): SkinDef {
        return SKINS.find { it.id == skinId } ?: SKINS.first()
    }
}
