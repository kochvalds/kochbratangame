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
        // ==================== КОЧ БРАТАН (KOCHVALDS) ====================
        SkinDef(
            id = "kochvalds_default",
            characterId = "kochvalds",
            name = "Коч Братан Классик",
            description = "Поло с полосатым воротником и фирменная ухмылка. Заряжен кочнуть в любой момент.",
            drawableRes = R.drawable.char_kochvalds_default,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.15,
            passiveMultiplierBonus = 0.15,
            adrenalineBonus = 0.30,
            badgeText = "БРАТАН"
        ),
        SkinDef(
            id = "kochvalds_beast",
            characterId = "kochvalds",
            name = "Коч Братан Титан Зала",
            description = "Огромные трапеции, спортивная майка и раскаленный адреналин. Жмет 140кг на разминке.",
            drawableRes = R.drawable.char_kochvalds_beast,
            reqStage = 2,
            costAura = 25000.0,
            clickMultiplierBonus = 0.45,
            passiveMultiplierBonus = 0.50,
            adrenalineBonus = 0.60,
            badgeText = "+50% ПАССИВ"
        ),
        SkinDef(
            id = "koch_berserk",
            characterId = "kochvalds",
            name = "Коч Братан Берсерк",
            description = "Пылающие вулканические вены, дикий рев и абсолютная ярость пампинга.",
            drawableRes = R.drawable.char_koch_berserk,
            reqStage = 3,
            costAura = 80000.0,
            clickMultiplierBonus = 0.80,
            passiveMultiplierBonus = 0.40,
            adrenalineBonus = 1.00, // удвоение скорости набора адреналина!
            badgeText = "БЕРСЕРК"
        ),
        SkinDef(
            id = "koch_olympia",
            characterId = "kochvalds",
            name = "Коч Братан Золотой Чемпион",
            description = "Сияющий золотой ореол, безупречная сепарация мышц и титул Мистер Олимпия Луксмакса.",
            drawableRes = R.drawable.char_koch_olympia,
            reqStage = 4,
            costAura = 250000.0,
            clickMultiplierBonus = 0.90,
            passiveMultiplierBonus = 1.20,
            adrenalineBonus = 0.80,
            badgeText = "ЗОЛОТОЙ"
        ),
        SkinDef(
            id = "koch_bonesmasher",
            characterId = "kochvalds",
            name = "Коч Братан Бог Бонсмешинга",
            description = "Алмазная челюсть с идеальным углом 90° и молот Тора для стимуляции костной ткани.",
            drawableRes = R.drawable.char_koch_bonesmasher,
            reqStage = 5,
            costAura = 1000000.0,
            clickMultiplierBonus = 1.50, // +150%
            passiveMultiplierBonus = 1.00,
            adrenalineBonus = 1.20,
            badgeText = "БОГ ЧЕЛЮСТИ"
        ),

        // ==================== ВЛАДОС (VLADOS) ====================
        SkinDef(
            id = "vlados_default",
            characterId = "vlados",
            name = "Владос Классик",
            description = "Фирменная полосатая рубашка и загадочная улыбка. Главный мастер мьюинга.",
            drawableRes = R.drawable.char_vlados_default,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.15,
            passiveMultiplierBonus = 0.05,
            adrenalineBonus = 0.15,
            badgeText = "СТАРТОВЫЙ"
        ),
        SkinDef(
            id = "vlados_sigma",
            characterId = "vlados",
            name = "Владос Апекс Сигма",
            description = "Острые скулы, стильный черный пиджак и неоновая аура холодного спокойствия.",
            drawableRes = R.drawable.char_vlados_sigma,
            reqStage = 2,
            costAura = 20000.0,
            clickMultiplierBonus = 0.50,
            passiveMultiplierBonus = 0.30,
            adrenalineBonus = 0.30,
            badgeText = "+50% КЛИК"
        ),
        SkinDef(
            id = "vlados_cyber",
            characterId = "vlados",
            name = "Владос Кибер-Моггер 2077",
            description = "Неоновые смарт-очки, микрочипы симметрии лица и футуристический стиль.",
            drawableRes = R.drawable.char_vlados_cyber,
            reqStage = 3,
            costAura = 100000.0,
            clickMultiplierBonus = 0.85,
            passiveMultiplierBonus = 0.70,
            adrenalineBonus = 0.50,
            badgeText = "КИБЕРПАНК"
        ),
        SkinDef(
            id = "vlados_emperor",
            characterId = "vlados",
            name = "Владос Император Луксмакса",
            description = "Золотой лавровый венок, пурпурная мантия и абсолютное признание среди моггеров.",
            drawableRes = R.drawable.char_vlados_emperor,
            reqStage = 5,
            costAura = 1200000.0,
            clickMultiplierBonus = 1.60,
            passiveMultiplierBonus = 1.40,
            adrenalineBonus = 1.00,
            badgeText = "ИМПЕРАТОР"
        ),

        // ==================== БРАТАН ТЁМЩИК ====================
        SkinDef(
            id = "temshik_default",
            characterId = "temshik",
            name = "Тёмщик на Суете",
            description = "Черное худи, золотая цепь, барсетка и айфон. В курсе всех криптотем и дропов ауры.",
            drawableRes = R.drawable.char_temshik,
            reqStage = 1,
            costAura = 10000.0,
            clickMultiplierBonus = 0.30,
            passiveMultiplierBonus = 0.60, // спекулянт ауры дает отличный пассивный доход
            adrenalineBonus = 0.35,
            badgeText = "+60% ПАССИВ"
        ),

        // ==================== ПРОФЕССОР МЬЮ ====================
        SkinDef(
            id = "prof_mew_default",
            characterId = "prof_mew",
            name = "Профессор Ортотропии",
            description = "Белый халат, очки и золотой калипер. Научно вычисляет угол челюсти до десятых долей градуса.",
            drawableRes = R.drawable.char_prof_mew,
            reqStage = 2,
            costAura = 40000.0,
            clickMultiplierBonus = 0.40,
            passiveMultiplierBonus = 0.80,
            adrenalineBonus = 0.20,
            badgeText = "НАУЧНЫЙ МЬЮИНГ"
        ),

        // ==================== ФОИДКА & ЧАД ====================
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
            costAura = 500000.0,
            clickMultiplierBonus = 1.00,
            passiveMultiplierBonus = 1.00,
            adrenalineBonus = 1.00,
            badgeText = "ЛЕГЕНДАРНЫЙ"
        )
    )

    val CHARACTERS = listOf(
        CharacterDef(
            id = "kochvalds",
            name = "Коч Братан",
            title = "Легенда Кочалки & Адреналиновый Бог",
            quote = "«Либо ты кочаешь братана, либо фоидки могают тебя! Жми до отказа!» 💪🔥",
            skins = SKINS.filter { it.characterId == "kochvalds" }
        ),
        CharacterDef(
            id = "vlados",
            name = "Владос",
            title = "Мастер Полосатого Стиля & Мьюинга",
            quote = "«Челюсть не ждёт, братан. Прижми язык к нёбу и могай без остановки!» 🤫🧏",
            skins = SKINS.filter { it.characterId == "vlados" }
        ),
        CharacterDef(
            id = "temshik",
            name = "Братан Тёмщик",
            title = "Гений Суеты & Крипто-Ауры",
            quote = "«Слышь, есть темка на миллиард ауры. Главное — вовремя войти в сделку!» 📱💼",
            skins = SKINS.filter { it.characterId == "temshik" }
        ),
        CharacterDef(
            id = "prof_mew",
            name = "Доктор Мью",
            title = "Основатель Ортотропии & Науки Челюсти",
            quote = "«Идеальный гониальный угол 90° — это не случайность, а законы физики!» 📐🧬",
            skins = SKINS.filter { it.characterId == "prof_mew" }
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
