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
        // ==================== ПЕНИСОВ 333 (НОВЫЙ ПЕРСОНАЖ) ====================
        SkinDef(
            id = "penisov_333",
            characterId = "penisov",
            name = "Пенисов 333 Классик",
            description = "Золотая цепь с кулоном 333, модные темные очки и победная ухмылка. Мастер блатных номеров.",
            drawableRes = R.drawable.char_penisov_333,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.40,
            passiveMultiplierBonus = 0.35,
            adrenalineBonus = 0.50,
            badgeText = "НОМЕР 333"
        ),
        SkinDef(
            id = "penisov_gold",
            characterId = "penisov",
            name = "Пенисов 333 Золотой Барон",
            description = "Золотой госномер Е333КХ 777 в руках, сияющая аура и личный гараж с гиперкарами.",
            drawableRes = R.drawable.char_penisov_gold,
            reqStage = 3,
            costAura = 180000.0,
            clickMultiplierBonus = 1.30,
            passiveMultiplierBonus = 1.50,
            adrenalineBonus = 1.00,
            badgeText = "ЗОЛОТОЙ 333"
        ),

        // ==================== БОРИС ОФНИК ====================
        SkinDef(
            id = "boris_ofnik",
            characterId = "boris",
            name = "Борис Офник Классик",
            description = "Капюшон с линзами CP Company, бейсболка и кроссы Spezial. Знает каждый закоулок на районе.",
            drawableRes = R.drawable.char_boris_ofnik,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.30,
            passiveMultiplierBonus = 0.10,
            adrenalineBonus = 0.35,
            badgeText = "КЭЖУАЛ"
        ),
        SkinDef(
            id = "boris_firm",
            characterId = "boris",
            name = "Борис Лидер Фирмы",
            description = "Патч Stone Island на рукаве, клубный шарф и боевой настрой. Околофутбол не прощает слабых!",
            drawableRes = R.drawable.char_boris_firm,
            reqStage = 2,
            costAura = 35000.0,
            clickMultiplierBonus = 0.65,
            passiveMultiplierBonus = 0.30,
            adrenalineBonus = 0.60,
            badgeText = "ОКОЛОФУТБОЛ"
        ),
        SkinDef(
            id = "boris_turnik",
            characterId = "boris",
            name = "Борис Турникмен Двора",
            description = "Спортивный костюм, выход силой на две руки и чистый дворовой воркаут до седьмого пота.",
            drawableRes = R.drawable.char_boris_turnik,
            reqStage = 3,
            costAura = 120000.0,
            clickMultiplierBonus = 0.85,
            passiveMultiplierBonus = 0.90,
            adrenalineBonus = 0.80,
            badgeText = "ВОРКАУТ"
        ),

        // ==================== ВАСЬКА СТРИГУН ====================
        SkinDef(
            id = "vaska_barber",
            characterId = "vaska",
            name = "Васька Стригун Классик",
            description = "Фартук барбера, машинка для стрижки и расческа. Делает бритвенно-четкий фейд за 5 минут.",
            drawableRes = R.drawable.char_vaska_barber,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.25,
            passiveMultiplierBonus = 0.50,
            adrenalineBonus = 0.20,
            badgeText = "ФЕЙД 10/10"
        ),
        SkinDef(
            id = "vaska_gold",
            characterId = "vaska",
            name = "Васька Мастер Баззкатов",
            description = "Золотой триммер, идеальная линия роста волос и височной зоны. Выравнивает симметрию черепа.",
            drawableRes = R.drawable.char_vaska_gold,
            reqStage = 2,
            costAura = 45000.0,
            clickMultiplierBonus = 0.70,
            passiveMultiplierBonus = 0.85,
            adrenalineBonus = 0.40,
            badgeText = "ЗОЛОТОЙ ТРИММЕР"
        ),
        SkinDef(
            id = "vaska_razor",
            characterId = "vaska",
            name = "Васька Опасная Бритва",
            description = "Опасная бритва из дамасской стали. Ювелирный срез волос и окантовка острее хирургического скальпеля.",
            drawableRes = R.drawable.char_vaska_razor,
            reqStage = 4,
            costAura = 300000.0,
            clickMultiplierBonus = 1.10,
            passiveMultiplierBonus = 1.10,
            adrenalineBonus = 0.75,
            badgeText = "ОПАСНАЯ БРИТВА"
        ),

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
            adrenalineBonus = 1.00,
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
            clickMultiplierBonus = 1.50,
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
            passiveMultiplierBonus = 0.60,
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
            id = "penisov",
            name = "Пенисов 333",
            title = "Король Блатных Номеров & Чистой Суеты",
            quote = "«Е333КХ 777 на связи, братуха! Выбивай блатные номера, заряжай шмотки и могай весь город!» 🚗🔢",
            skins = SKINS.filter { it.characterId == "penisov" }
        ),
        CharacterDef(
            id = "boris",
            name = "Борис Офник",
            title = "Легенда Околофутбола & Кэжуал-Стиля",
            quote = "«Поясни за шмот и гониальный угол, братуха! Линзы на капюшоне заряжены на победу!» 🧢👟",
            skins = SKINS.filter { it.characterId == "boris" }
        ),
        CharacterDef(
            id = "vaska",
            name = "Васька Стригун",
            title = "Маэстро Баззкатов & Барбер Луксмакса",
            quote = "«Стригу под ноль с идеальным фейдом! Ровная линия роста волос поднимает PSL на +2 пункта!» ✂️💈",
            skins = SKINS.filter { it.characterId == "vaska" }
        ),
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
