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
        // ==================== ПЕНИСОВ 333 ====================
        SkinDef(
            id = "penisov_333",
            characterId = "penisov",
            name = "Пенисов 333 Классик",
            description = "Золотая цепь с кулоном 333, модные темные очки и победная ухмылка. Король блатных номеров.",
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
            reqStage = 2,
            costAura = 180000.0,
            clickMultiplierBonus = 1.30,
            passiveMultiplierBonus = 1.50,
            adrenalineBonus = 1.00,
            badgeText = "ЗОЛОТОЙ 333"
        ),
        SkinDef(
            id = "penisov_pisyun",
            characterId = "penisov",
            name = "Пенисов 333 Апекс Писюн",
            description = "Заряжен на максимальный альфа-моггинг, блатная аура на 333% и неоновый блеск.",
            drawableRes = R.drawable.char_penisov_gold,
            reqStage = 4,
            costAura = 888888.0,
            clickMultiplierBonus = 2.00,
            passiveMultiplierBonus = 2.00,
            adrenalineBonus = 1.50,
            badgeText = "АПЕКС 333"
        ),

        // ==================== 1. СЕРЁГА СКУФ ====================
        SkinDef(
            id = "skuf_classic",
            characterId = "skuf",
            name = "Серёга Скуф Диванный",
            description = "Майка-алкоголичка, кружка пенного и танки на экране. Заслуженный мастер диванного моггинга.",
            drawableRes = R.drawable.char_boris_ofnik,
            reqStage = 0,
            costAura = 0.0,
            clickMultiplierBonus = 0.20,
            passiveMultiplierBonus = 0.35,
            adrenalineBonus = 0.20,
            badgeText = "СКУФ"
        ),
        SkinDef(
            id = "skuf_tankist",
            characterId = "skuf",
            name = "Скуф Командир Танка",
            description = "Шлемофон танкиста, медаль «За взятие Малиновки» и двойной залп по фоидкам.",
            drawableRes = R.drawable.char_boris_firm,
            reqStage = 2,
            costAura = 50000.0,
            clickMultiplierBonus = 0.60,
            passiveMultiplierBonus = 0.70,
            adrenalineBonus = 0.40,
            badgeText = "ТАНКИСТ"
        ),

        // ==================== 2. АРТЁМ ТЕМЩИК V2 ====================
        SkinDef(
            id = "temshik_v2_dubai",
            characterId = "temshik_v2",
            name = "Артём Темщик V2 в Дубае",
            description = "Белые льняные брюки, очки Cartier, созвоны в Zoom прямо с яхты. Крипто-арбитраж 24/7.",
            drawableRes = R.drawable.char_temshik,
            reqStage = 1,
            costAura = 40000.0,
            clickMultiplierBonus = 0.50,
            passiveMultiplierBonus = 0.85,
            adrenalineBonus = 0.35,
            badgeText = "ДУБАЙ"
        ),

        // ==================== 3. МАГА БОРЦУХА ====================
        SkinDef(
            id = "maga_wrestler",
            characterId = "maga",
            name = "Мага Борцуха Тигр",
            description = "Сломанные уши, белая папаха, красное трико. Бросок с прогиба выносит фоидку с одного клика.",
            drawableRes = R.drawable.char_koch_berserk,
            reqStage = 2,
            costAura = 65000.0,
            clickMultiplierBonus = 0.85,
            passiveMultiplierBonus = 0.40,
            adrenalineBonus = 0.90,
            badgeText = "БОРЦУХА"
        ),

        // ==================== 4. ДИМОН ДРИФТЕР ====================
        SkinDef(
            id = "dimon_drift_king",
            characterId = "dimon_drift",
            name = "Димон Дрифтер Парковочный",
            description = "Заваренный редуктор, вывернутый руль и дым от жженой резины под ночным фонарем.",
            drawableRes = R.drawable.char_vlados_cyber,
            reqStage = 1,
            costAura = 30000.0,
            clickMultiplierBonus = 0.75,
            passiveMultiplierBonus = 0.50,
            adrenalineBonus = 0.60,
            badgeText = "ДРИФТ"
        ),

        // ==================== 5. ПАША ДУРОВ СИГМА ====================
        SkinDef(
            id = "durov_sigma",
            characterId = "durov",
            name = "Паша Дуров Ледяной Апекс",
            description = "Черная водолазка, кубики пресса, погружение в ванну со льдом. Абсолютный цифровой суверенитет.",
            drawableRes = R.drawable.char_vlados_sigma,
            reqStage = 3,
            costAura = 200000.0,
            clickMultiplierBonus = 1.30,
            passiveMultiplierBonus = 1.20,
            adrenalineBonus = 0.70,
            badgeText = "ДУРОВ"
        ),

        // ==================== 6. САНЯ АВТОРИТЕТ ====================
        SkinDef(
            id = "sanya_boss_classic",
            characterId = "sanya_boss",
            name = "Саня Авторитет Двора",
            description = "Спортивки с тремя полосками, кепка-восьмиклинка, четки и горсть отборных жареных семок.",
            drawableRes = R.drawable.char_boris_ofnik,
            reqStage = 1,
            costAura = 25000.0,
            clickMultiplierBonus = 0.55,
            passiveMultiplierBonus = 0.45,
            adrenalineBonus = 0.50,
            badgeText = "АВТОРИТЕТ"
        ),

        // ==================== 7. ИЛЮХА ЗАБИВНОЙ ====================
        SkinDef(
            id = "zabivnoy_flame",
            characterId = "zabivnoy",
            name = "Илюха Забивной Фаер",
            description = "Балаклава, черный анорак, горящий красный фаер в руке. Стенка на стенку до победы.",
            drawableRes = R.drawable.char_boris_firm,
            reqStage = 2,
            costAura = 75000.0,
            clickMultiplierBonus = 0.95,
            passiveMultiplierBonus = 0.50,
            adrenalineBonus = 0.95,
            badgeText = "ЗАБИВНОЙ"
        ),

        // ==================== 8. ДЕД МОГГЕР ====================
        SkinDef(
            id = "ded_turnik",
            characterId = "ded_mogger",
            name = "Дед Моггер Ветеран СССР",
            description = "Трико СССР, советские кеды, крутит солнце на турнике в 75 лет. Железная хватка.",
            drawableRes = R.drawable.char_boris_turnik,
            reqStage = 2,
            costAura = 90000.0,
            clickMultiplierBonus = 0.70,
            passiveMultiplierBonus = 0.95,
            adrenalineBonus = 0.60,
            badgeText = "ВЕТЕРАН"
        ),

        // ==================== 9. КИРИЛЛ РУКИ-БАЗУКИ ====================
        SkinDef(
            id = "bazooka_power",
            characterId = "bazooka",
            name = "Кирилл Руки-Базуки 60 см",
            description = "Гигантские банки по 60 см, знаменитая двоечка в воздух и взрывной хайп.",
            drawableRes = R.drawable.char_kochvalds_beast,
            reqStage = 3,
            costAura = 150000.0,
            clickMultiplierBonus = 1.15,
            passiveMultiplierBonus = 0.60,
            adrenalineBonus = 1.10,
            badgeText = "БАЗУКИ"
        ),

        // ==================== 10. МИША МАЖОР ПАТРИКИ ====================
        SkinDef(
            id = "major_patriki",
            characterId = "major",
            name = "Миша Мажор на Патриках",
            description = "Шарф Gucci, стаканчик спешелти рафа на кокосовом, папина карта безлимит. Люкс 24/7.",
            drawableRes = R.drawable.char_vlados_emperor,
            reqStage = 4,
            costAura = 400000.0,
            clickMultiplierBonus = 1.00,
            passiveMultiplierBonus = 1.60,
            adrenalineBonus = 0.60,
            badgeText = "МАЖОР"
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
            id = "skuf",
            name = "Серёга Скуф",
            title = "Ветеран Танков & Диванного Моггинга",
            quote = "«Танки сами себя не победят, братан! Пивко открыто, диван продавлен, могаем не вставая!» 🍺🛋️",
            skins = SKINS.filter { it.characterId == "skuf" }
        ),
        CharacterDef(
            id = "temshik_v2",
            name = "Артём Темщик V2",
            title = "Мастер Арбитража & Созвонов в Дубае",
            quote = "«Братан, залетай на созвон в Zoom! Арбитраж ауры приносит 100 000 в минуту!» 📱💼",
            skins = SKINS.filter { it.characterId == "temshik_v2" }
        ),
        CharacterDef(
            id = "maga",
            name = "Мага Борцуха",
            title = "Чемпион по Вольной Борьбе & Прогибам",
            quote = "«С прогиба кину любого фоидку! Сломанные уши — знак настоящего тигра!» 🤼‍♂️🐯",
            skins = SKINS.filter { it.characterId == "maga" }
        ),
        CharacterDef(
            id = "dimon_drift",
            name = "Димон Дрифтер",
            title = "Король Ночной Парковки & Жженой Резины",
            quote = "«Заварил редуктор — заварил победу! Валим боком в 2 часа ночи на парковке!» 💨🏎️",
            skins = SKINS.filter { it.characterId == "dimon_drift" }
        ),
        CharacterDef(
            id = "durov",
            name = "Паша Дуров",
            title = "Создатель Цифровой Свободы & Ледяных Ванн",
            quote = "«Принял ледяную ванну, покачал пресс и могаю этот мир в полном молчании!» 🧊🖤",
            skins = SKINS.filter { it.characterId == "durov" }
        ),
        CharacterDef(
            id = "sanya_boss",
            name = "Саня Авторитет",
            title = "Смотрящий за Районом & Семками",
            quote = "«На районе всё спокойно, когда сигмы держат порядок. Лови горсть семок, братуха!» 🧢🌻",
            skins = SKINS.filter { it.characterId == "sanya_boss" }
        ),
        CharacterDef(
            id = "zabivnoy",
            name = "Илюха Забивной",
            title = "Гроза Полян & Околофутбола",
            quote = "«Один за всех и все за одного! Зажигай фаера, погнали на забив!» 🔥👊",
            skins = SKINS.filter { it.characterId == "zabivnoy" }
        ),
        CharacterDef(
            id = "ded_mogger",
            name = "Дед Моггер",
            title = "Ветеран Советской Гимнастики с 1975 года",
            quote = "«В моё время мьюингом не называли, а просто держали осанку и крутили солнышко!» 👴🥇",
            skins = SKINS.filter { it.characterId == "ded_mogger" }
        ),
        CharacterDef(
            id = "bazooka",
            name = "Кирилл Руки-Базуки",
            title = "Обладатель Базук & Двоечки в Воздух",
            quote = "«Руки-базуки на месте! Двоечка по воздуху заряжает ауру на миллион!» 💪💥",
            skins = SKINS.filter { it.characterId == "bazooka" }
        ),
        CharacterDef(
            id = "major",
            name = "Миша Мажор",
            title = "Завсегдатай Патриков & Золотой Мальчик",
            quote = "«Папина карточка безлимитная, беру раф на кокосовом и еду могать на Патрики!» ☕💳",
            skins = SKINS.filter { it.characterId == "major" }
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
