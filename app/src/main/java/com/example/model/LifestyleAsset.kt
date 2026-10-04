package com.example.model

import kotlin.random.Random

enum class PlateRarity(val titleRu: String, val colorHex: Long) {
    COMMON("Обычный", 0xFF9E9E9E),
    RARE("Зеркальный", 0xFF00E5FF),
    EPIC("Блатной", 0xFFD500F9),
    LEGENDARY("Правительственный", 0xFFFFD600)
}

data class RussianPlate(
    val fullPlate: String,
    val seriesFirst: String,
    val number: String,
    val seriesRest: String,
    val region: String,
    val rarity: PlateRarity,
    val specialName: String,
    val clickBonus: Double,
    val passiveBonus: Double
)

data class CarAsset(
    val id: String,
    val name: String,
    val brand: String,
    val description: String,
    val iconEmoji: String,
    val costAura: Double,
    val clickMultiplierBonus: Double,
    val passiveMultiplierBonus: Double,
    val speedKmh: Int,
    val horsepower: Int
)

data class RealEstateAsset(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val iconEmoji: String,
    val costAura: Double,
    val passiveAuraPerSec: Double,
    val auraMultiplierBonus: Double
)

data class CryptoCoin(
    val symbol: String,
    val name: String,
    val iconEmoji: String,
    val basePriceAura: Double,
    val currentPriceAura: Double,
    val change24hPercent: Double,
    val ownedAmount: Double
)

object LifestyleCatalog {
    val CARS = listOf(
        CarAsset(
            id = "vaz_2101",
            name = "ВАЗ 2101 «Копейка»",
            brand = "LADA",
            description = "Классика советского автопрома, круглые фары, хромированные колпаки.",
            iconEmoji = "🚗",
            costAura = 1000.0,
            clickMultiplierBonus = 0.05,
            passiveMultiplierBonus = 0.05,
            speedKmh = 140,
            horsepower = 64
        ),
        CarAsset(
            id = "vaz_2102",
            name = "ВАЗ 2102 «Двоечка»",
            brand = "LADA",
            description = "Универсал для дачи, мешков картошки и уверенного моггинга.",
            iconEmoji = "🚙",
            costAura = 1500.0,
            clickMultiplierBonus = 0.07,
            passiveMultiplierBonus = 0.06,
            speedKmh = 135,
            horsepower = 64
        ),
        CarAsset(
            id = "vaz_2103",
            name = "ВАЗ 2103 «Тройка»",
            brand = "LADA",
            description = "Люксовая классика СССР, четыре фары и велюровый салон.",
            iconEmoji = "🚗",
            costAura = 2200.0,
            clickMultiplierBonus = 0.09,
            passiveMultiplierBonus = 0.08,
            speedKmh = 150,
            horsepower = 75
        ),
        CarAsset(
            id = "vaz_2104",
            name = "ВАЗ 2104 «Четверка»",
            brand = "LADA",
            description = "Рабочая лошадка с багажником на крыше, незаменима в хозяйстве.",
            iconEmoji = "🚙",
            costAura = 3000.0,
            clickMultiplierBonus = 0.12,
            passiveMultiplierBonus = 0.1,
            speedKmh = 145,
            horsepower = 72
        ),
        CarAsset(
            id = "vaz_2105",
            name = "ВАЗ 2105 «Пятерка»",
            brand = "LADA",
            description = "Прямоугольные фары, строгий дизайн, готова к зимнему дрифту.",
            iconEmoji = "🚗",
            costAura = 3800.0,
            clickMultiplierBonus = 0.15,
            passiveMultiplierBonus = 0.12,
            speedKmh = 150,
            horsepower = 71
        ),
        CarAsset(
            id = "vaz_2106",
            name = "ВАЗ 2106 «Шаха»",
            brand = "LADA",
            description = "Шестерка на басистом выхлопе, розочка на рычаге КПП.",
            iconEmoji = "🚗",
            costAura = 4500.0,
            clickMultiplierBonus = 0.18,
            passiveMultiplierBonus = 0.14,
            speedKmh = 155,
            horsepower = 75
        ),
        CarAsset(
            id = "vaz_2107",
            name = "ВАЗ 2107 «Боевая Классика»",
            brand = "LADA",
            description = "Тонировка в круг, заварка, красноярский выворот, суета на парковке.",
            iconEmoji = "🚗",
            costAura = 5500.0,
            clickMultiplierBonus = 0.2,
            passiveMultiplierBonus = 0.15,
            speedKmh = 160,
            horsepower = 85
        ),
        CarAsset(
            id = "vaz_2108",
            name = "ВАЗ 2108 «Восьмерка»",
            brand = "LADA",
            description = "Легендарное зубило, короткое крыло, спортивный советский хэтч.",
            iconEmoji = "🏎️",
            costAura = 7000.0,
            clickMultiplierBonus = 0.24,
            passiveMultiplierBonus = 0.18,
            speedKmh = 165,
            horsepower = 80
        ),
        CarAsset(
            id = "vaz_2109",
            name = "ВАЗ 2109 «Девятка»",
            brand = "LADA",
            description = "Вишневая девятка, мечта бандитов 90-х, динамики на полку.",
            iconEmoji = "🚗",
            costAura = 8500.0,
            clickMultiplierBonus = 0.28,
            passiveMultiplierBonus = 0.2,
            speedKmh = 168,
            horsepower = 78
        ),
        CarAsset(
            id = "vaz_21099",
            name = "ВАЗ 21099 «Дуплет»",
            brand = "LADA",
            description = "Седан из семейства Самара, спойлер на багажнике, четкий аппарат.",
            iconEmoji = "🚗",
            costAura = 10000.0,
            clickMultiplierBonus = 0.32,
            passiveMultiplierBonus = 0.22,
            speedKmh = 170,
            horsepower = 78
        ),
        CarAsset(
            id = "vaz_2110",
            name = "ВАЗ 2110 «Десятка»",
            brand = "LADA",
            description = "Обтекаемый кузов, гидроусилитель и европанель.",
            iconEmoji = "🚗",
            costAura = 12000.0,
            clickMultiplierBonus = 0.35,
            passiveMultiplierBonus = 0.25,
            speedKmh = 175,
            horsepower = 89
        ),
        CarAsset(
            id = "vaz_2111",
            name = "ВАЗ 2111 «Одиннадцатая»",
            brand = "LADA",
            description = "Семейный универсал десятого семейства, вместительный и надежный.",
            iconEmoji = "🚙",
            costAura = 13500.0,
            clickMultiplierBonus = 0.37,
            passiveMultiplierBonus = 0.26,
            speedKmh = 172,
            horsepower = 89
        ),
        CarAsset(
            id = "vaz_2112",
            name = "ВАЗ 2112 «Двенашка»",
            brand = "LADA",
            description = "16-клапанный шеснарь 124 мотор, не гнет клапана, суетит на трассе.",
            iconEmoji = "🏎️",
            costAura = 15000.0,
            clickMultiplierBonus = 0.4,
            passiveMultiplierBonus = 0.28,
            speedKmh = 185,
            horsepower = 98
        ),
        CarAsset(
            id = "vaz_2113",
            name = "ВАЗ 2113 «Тринашка»",
            brand = "LADA",
            description = "Самара-2 в трехдверном кузове, дерзкий вид и легкий вес.",
            iconEmoji = "🚗",
            costAura = 18000.0,
            clickMultiplierBonus = 0.42,
            passiveMultiplierBonus = 0.3,
            speedKmh = 180,
            horsepower = 82
        ),
        CarAsset(
            id = "vaz_2114",
            name = "ВАЗ 2114 «Четырка»",
            brand = "LADA",
            description = "Посаженная четырка, вспышки ФСО под решеткой, темные стекла.",
            iconEmoji = "🚗",
            costAura = 22000.0,
            clickMultiplierBonus = 0.45,
            passiveMultiplierBonus = 0.32,
            speedKmh = 185,
            horsepower = 82
        ),
        CarAsset(
            id = "vaz_2115",
            name = "ВАЗ 2115 «Пятнашка»",
            brand = "LADA",
            description = "Заводской спойлер со стоп-сигналом, классика провинциальных дорог.",
            iconEmoji = "🚗",
            costAura = 25000.0,
            clickMultiplierBonus = 0.48,
            passiveMultiplierBonus = 0.35,
            speedKmh = 180,
            horsepower = 82
        ),
        CarAsset(
            id = "priora_sedan",
            name = "LADA Priora Седан",
            brand = "LADA",
            description = "Черная Приора на пневме, посадка в пол, сабвуфер на весь багажник.",
            iconEmoji = "🏎️",
            costAura = 30000.0,
            clickMultiplierBonus = 0.52,
            passiveMultiplierBonus = 0.38,
            speedKmh = 190,
            horsepower = 98
        ),
        CarAsset(
            id = "priora_hatch",
            name = "LADA Priora Хэтчбек",
            brand = "LADA",
            description = "Шеснарь на валах, ресивер ProCar, отсечка на 7500 об/мин.",
            iconEmoji = "🏎️",
            costAura = 35000.0,
            clickMultiplierBonus = 0.55,
            passiveMultiplierBonus = 0.4,
            speedKmh = 195,
            horsepower = 106
        ),
        CarAsset(
            id = "priora_coupe",
            name = "LADA Priora Купе",
            brand = "LADA",
            description = "Редкая трехдверная версия, заводской спорт-пакет, вид на миллион.",
            iconEmoji = "🏎️",
            costAura = 38000.0,
            clickMultiplierBonus = 0.57,
            passiveMultiplierBonus = 0.41,
            speedKmh = 195,
            horsepower = 106
        ),
        CarAsset(
            id = "kalina_sport",
            name = "LADA Kalina Sport",
            brand = "LADA",
            description = "Дисковые тормоза в круг, желтый цвет, спортивные ковши.",
            iconEmoji = "🚗",
            costAura = 40000.0,
            clickMultiplierBonus = 0.58,
            passiveMultiplierBonus = 0.42,
            speedKmh = 197,
            horsepower = 118
        ),
        CarAsset(
            id = "granta_sport",
            name = "LADA Granta Sport",
            brand = "LADA",
            description = "Обвес, занижение, выхлоп с басовитым звуком, выбор молодых сигм.",
            iconEmoji = "🏎️",
            costAura = 45000.0,
            clickMultiplierBonus = 0.62,
            passiveMultiplierBonus = 0.45,
            speedKmh = 200,
            horsepower = 118
        ),
        CarAsset(
            id = "vesta_sport",
            name = "LADA Vesta Sportline",
            brand = "LADA",
            description = "Акулий плавник, 17-дюймовые диски, светодиодная оптика LED.",
            iconEmoji = "🚗",
            costAura = 55000.0,
            clickMultiplierBonus = 0.68,
            passiveMultiplierBonus = 0.5,
            speedKmh = 205,
            horsepower = 145
        ),
        CarAsset(
            id = "niva_legend",
            name = "LADA Niva Legend 4x4",
            brand = "LADA",
            description = "Постоянный полный привод, блокировка дифференциала, проедет везде.",
            iconEmoji = "🚙",
            costAura = 48000.0,
            clickMultiplierBonus = 0.65,
            passiveMultiplierBonus = 0.48,
            speedKmh = 140,
            horsepower = 83
        ),
        CarAsset(
            id = "niva_travel",
            name = "LADA Niva Travel",
            brand = "LADA",
            description = "Шноркель, внедорожные шины грязевой протектор, экспедиционный вид.",
            iconEmoji = "🚙",
            costAura = 60000.0,
            clickMultiplierBonus = 0.7,
            passiveMultiplierBonus = 0.52,
            speedKmh = 145,
            horsepower = 80
        ),
        CarAsset(
            id = "lada_largus",
            name = "LADA Largus Cross VIP",
            brand = "LADA",
            description = "Практичный гигант на высоком клиренсе, вместит всю бригаду.",
            iconEmoji = "🚐",
            costAura = 50000.0,
            clickMultiplierBonus = 0.66,
            passiveMultiplierBonus = 0.49,
            speedKmh = 165,
            horsepower = 106
        ),
        CarAsset(
            id = "gaz_21",
            name = "ГАЗ 21 «Волга»",
            brand = "ГАЗ",
            description = "Олень на капоте, диван спереди, символ советского триумфа.",
            iconEmoji = "🚗",
            costAura = 35000.0,
            clickMultiplierBonus = 0.55,
            passiveMultiplierBonus = 0.4,
            speedKmh = 130,
            horsepower = 75
        ),
        CarAsset(
            id = "gaz_24",
            name = "ГАЗ 24 «Волга» Баржа",
            brand = "ГАЗ",
            description = "Черная директорская Волга, хромированная решетка, авторитет.",
            iconEmoji = "🚗",
            costAura = 42000.0,
            clickMultiplierBonus = 0.6,
            passiveMultiplierBonus = 0.44,
            speedKmh = 150,
            horsepower = 95
        ),
        CarAsset(
            id = "gaz_24_v8",
            name = "ГАЗ 24-24 «Догонялка» V8",
            brand = "ГАЗ",
            description = "Секретная машина КГБ, мотор V8 от Чайки 195 л.с., маскировка.",
            iconEmoji = "🏎️",
            costAura = 85000.0,
            clickMultiplierBonus = 0.85,
            passiveMultiplierBonus = 0.65,
            speedKmh = 185,
            horsepower = 195
        ),
        CarAsset(
            id = "gaz_3102",
            name = "ГАЗ 3102 Номенклатурная",
            brand = "ГАЗ",
            description = "Строгий правительственный седан, форкамерный мотор, статус.",
            iconEmoji = "🚗",
            costAura = 55000.0,
            clickMultiplierBonus = 0.7,
            passiveMultiplierBonus = 0.52,
            speedKmh = 160,
            horsepower = 105
        ),
        CarAsset(
            id = "gaz_31105",
            name = "ГАЗ 31105 с крайслером",
            brand = "ГАЗ",
            description = "Мотор Chrysler 2.4 DOHC, немецкий салон, плавная баржа.",
            iconEmoji = "🚗",
            costAura = 65000.0,
            clickMultiplierBonus = 0.75,
            passiveMultiplierBonus = 0.56,
            speedKmh = 175,
            horsepower = 137
        ),
        CarAsset(
            id = "chaika_13",
            name = "ГАЗ 13 «Чайка»",
            brand = "ГАЗ",
            description = "Двухцветный кузов, роскошный салон, машина послов и космонавтов.",
            iconEmoji = "👑",
            costAura = 200000.0,
            clickMultiplierBonus = 1.1,
            passiveMultiplierBonus = 0.95,
            speedKmh = 160,
            horsepower = 195
        ),
        CarAsset(
            id = "chaika_14",
            name = "ГАЗ 14 «Чайка» Лимузин",
            brand = "ГАЗ",
            description = "Удлиненный правительственный лимузин высшего советского руководства.",
            iconEmoji = "👑",
            costAura = 280000.0,
            clickMultiplierBonus = 1.25,
            passiveMultiplierBonus = 1.1,
            speedKmh = 175,
            horsepower = 220
        ),
        CarAsset(
            id = "moskvich_408",
            name = "Москвич 408 Турист",
            brand = "Москвич",
            description = "Четырехфарный стиляга 60-х, экспортный хит в Европе.",
            iconEmoji = "🚗",
            costAura = 20000.0,
            clickMultiplierBonus = 0.45,
            passiveMultiplierBonus = 0.35,
            speedKmh = 130,
            horsepower = 50
        ),
        CarAsset(
            id = "moskvich_412",
            name = "Москвич 412 Ралли Лондон",
            brand = "Москвич",
            description = "Победитель трансконтинентального ралли Лондон-Сидней.",
            iconEmoji = "🏎️",
            costAura = 32000.0,
            clickMultiplierBonus = 0.53,
            passiveMultiplierBonus = 0.4,
            speedKmh = 145,
            horsepower = 75
        ),
        CarAsset(
            id = "moskvich_2140",
            name = "Москвич 2140 SL Люкс",
            brand = "Москвич",
            description = "Пластиковые бампера, югославская оптика, велюр.",
            iconEmoji = "🚗",
            costAura = 28000.0,
            clickMultiplierBonus = 0.5,
            passiveMultiplierBonus = 0.38,
            speedKmh = 142,
            horsepower = 75
        ),
        CarAsset(
            id = "moskvich_svyatogor",
            name = "Москвич Святогор Renault",
            brand = "Москвич",
            description = "Двухлитровый F3R Renault, рвет со светофора многие иномарки.",
            iconEmoji = "🏎️",
            costAura = 45000.0,
            clickMultiplierBonus = 0.63,
            passiveMultiplierBonus = 0.46,
            speedKmh = 180,
            horsepower = 112
        ),
        CarAsset(
            id = "zaz_968",
            name = "ЗАЗ 968М «Запорожец»",
            brand = "ЗАЗ",
            description = "Воздушное охлаждение, уши-воздухозаборники, задний привод.",
            iconEmoji = "🚗",
            costAura = 8000.0,
            clickMultiplierBonus = 0.25,
            passiveMultiplierBonus = 0.2,
            speedKmh = 120,
            horsepower = 40
        ),
        CarAsset(
            id = "zaz_tavria",
            name = "ЗАЗ 1102 Таврия Спорт",
            brand = "ЗАЗ",
            description = "Легкий украинский хэтчбек, доработанная ГБЦ, прострелы.",
            iconEmoji = "🏎️",
            costAura = 16000.0,
            clickMultiplierBonus = 0.38,
            passiveMultiplierBonus = 0.28,
            speedKmh = 155,
            horsepower = 65
        ),
        CarAsset(
            id = "luaz_969",
            name = "ЛуАЗ 969 «Волынь»",
            brand = "ЛуАЗ",
            description = "Колесные редукторы, блокировки, гребет по любому болоту.",
            iconEmoji = "🚙",
            costAura = 30000.0,
            clickMultiplierBonus = 0.51,
            passiveMultiplierBonus = 0.38,
            speedKmh = 100,
            horsepower = 40
        ),
        CarAsset(
            id = "uaz_hunter",
            name = "УАЗ Хантер «Козел»",
            brand = "УАЗ",
            description = "Военные мосты, брезентовый тент, танк для бездорожья.",
            iconEmoji = "🚙",
            costAura = 50000.0,
            clickMultiplierBonus = 0.66,
            passiveMultiplierBonus = 0.48,
            speedKmh = 130,
            horsepower = 112
        ),
        CarAsset(
            id = "uaz_buhanka",
            name = "УАЗ «Буханка» Экспедиция",
            brand = "УАЗ",
            description = "Легендарный батон на злой резине с лебедкой и багажником.",
            iconEmoji = "🚐",
            costAura = 52000.0,
            clickMultiplierBonus = 0.67,
            passiveMultiplierBonus = 0.5,
            speedKmh = 125,
            horsepower = 112
        ),
        CarAsset(
            id = "uaz_patriot",
            name = "УАЗ Патриот Трофи",
            brand = "УАЗ",
            description = "Большой рамный внедорожник, раздатка, уверенность в лесу.",
            iconEmoji = "🚙",
            costAura = 65000.0,
            clickMultiplierBonus = 0.72,
            passiveMultiplierBonus = 0.54,
            speedKmh = 150,
            horsepower = 150
        ),
        CarAsset(
            id = "gazelle_next",
            name = "ГАЗель Next Маршрутка",
            brand = "ГАЗ",
            description = "Легендарный дрифт маршрутки в час пик, турбодизель Cummins.",
            iconEmoji = "🚐",
            costAura = 58000.0,
            clickMultiplierBonus = 0.69,
            passiveMultiplierBonus = 0.52,
            speedKmh = 140,
            horsepower = 150
        ),
        CarAsset(
            id = "sobol_4x4",
            name = "Соболь 4x4 Автодом",
            brand = "ГАЗ",
            description = "Внедорожный минивэн с блокировками, готов к покорению Сибири.",
            iconEmoji = "🚐",
            costAura = 70000.0,
            clickMultiplierBonus = 0.75,
            passiveMultiplierBonus = 0.58,
            speedKmh = 135,
            horsepower = 120
        ),
        CarAsset(
            id = "zil_130",
            name = "ЗИЛ 130 Синий самосвал",
            brand = "ЗИЛ",
            description = "Звук мотора V8 6.0, белая морда, строитель советских городов.",
            iconEmoji = "🚛",
            costAura = 90000.0,
            clickMultiplierBonus = 0.88,
            passiveMultiplierBonus = 0.68,
            speedKmh = 90,
            horsepower = 150
        ),
        CarAsset(
            id = "kamaz_dakar",
            name = "КамАЗ-Мастер Дакар",
            brand = "КАМАЗ",
            description = "1000 л.с., многократный чемпион ралли Дакар, летит по барханам.",
            iconEmoji = "🏆",
            costAura = 500000.0,
            clickMultiplierBonus = 1.6,
            passiveMultiplierBonus = 1.4,
            speedKmh = 165,
            horsepower = 1050
        ),
        CarAsset(
            id = "ural_4320",
            name = "Урал 4320 Полярник",
            brand = "Урал",
            description = "6x6 вездеход, преодолевает броды 1.5 метра и вечную мерзлоту.",
            iconEmoji = "🚛",
            costAura = 350000.0,
            clickMultiplierBonus = 1.35,
            passiveMultiplierBonus = 1.15,
            speedKmh = 85,
            horsepower = 240
        ),
        CarAsset(
            id = "belaz_75710",
            name = "БелАЗ 75710 Карьерный Титан",
            brand = "БелАЗ",
            description = "Самый большой самосвал в мире, колеса 4 метра, 4600 сил!",
            iconEmoji = "⚡",
            costAura = 2500000.0,
            clickMultiplierBonus = 2.5,
            passiveMultiplierBonus = 2.3,
            speedKmh = 64,
            horsepower = 4600
        ),
        CarAsset(
            id = "auras_senat",
            name = "Aurus Senat Президентский",
            brand = "Aurus",
            description = "Бронированный флагман РФ, гибридный V8 битурбо, 600 сил.",
            iconEmoji = "👑",
            costAura = 1800000.0,
            clickMultiplierBonus = 2.2,
            passiveMultiplierBonus = 2.0,
            speedKmh = 250,
            horsepower = 598
        ),
        CarAsset(
            id = "auras_komendant",
            name = "Aurus Komendant Внедорожник",
            brand = "Aurus",
            description = "Премиальный полноразмерный люкс-SUV высшего эшелона.",
            iconEmoji = "👑",
            costAura = 2000000.0,
            clickMultiplierBonus = 2.3,
            passiveMultiplierBonus = 2.1,
            speedKmh = 240,
            horsepower = 598
        ),
        CarAsset(
            id = "chaser_jzx100",
            name = "Toyota Chaser JZX100 Tourer V",
            brand = "Toyota",
            description = "1JZ-GTE твинскролл, обвес Vertex, звук блоу-офф и угол заноса.",
            iconEmoji = "🏎️",
            costAura = 120000.0,
            clickMultiplierBonus = 0.95,
            passiveMultiplierBonus = 0.8,
            speedKmh = 260,
            horsepower = 280
        ),
        CarAsset(
            id = "mark2_jzx90",
            name = "Toyota Mark II JZX90 Самурай",
            brand = "Toyota",
            description = "Легендарный самурай, темный салон, банка выхлопа HKS.",
            iconEmoji = "🏎️",
            costAura = 110000.0,
            clickMultiplierBonus = 0.9,
            passiveMultiplierBonus = 0.75,
            speedKmh = 250,
            horsepower = 280
        ),
        CarAsset(
            id = "cresta_jzx100",
            name = "Toyota Cresta Roulant G",
            brand = "Toyota",
            description = "Рамочные двери, строгий комфорт и турбовый подхват.",
            iconEmoji = "🚗",
            costAura = 105000.0,
            clickMultiplierBonus = 0.88,
            passiveMultiplierBonus = 0.72,
            speedKmh = 250,
            horsepower = 280
        ),
        CarAsset(
            id = "supra_a80",
            name = "Toyota Supra A80 2JZ-GTE",
            brand = "Toyota",
            description = "Легендарный 2JZ на 1000 сил, антикрыло, икона форсажа.",
            iconEmoji = "⚡",
            costAura = 450000.0,
            clickMultiplierBonus = 1.5,
            passiveMultiplierBonus = 1.3,
            speedKmh = 310,
            horsepower = 330
        ),
        CarAsset(
            id = "supra_a90",
            name = "Toyota GR Supra A90",
            brand = "Toyota",
            description = "3.0 B58 Turbo, карбоновый диффузор, идеальная развесовка 50:50.",
            iconEmoji = "🏎️",
            costAura = 350000.0,
            clickMultiplierBonus = 1.35,
            passiveMultiplierBonus = 1.15,
            speedKmh = 280,
            horsepower = 387
        ),
        CarAsset(
            id = "ae86_trueno",
            name = "Toyota Sprinter Trueno AE86",
            brand = "Toyota",
            description = "Слепые фары, 4A-GE крутится до 9000 об/мин, доставка тофу.",
            iconEmoji = "🏎️",
            costAura = 150000.0,
            clickMultiplierBonus = 1.05,
            passiveMultiplierBonus = 0.85,
            speedKmh = 200,
            horsepower = 130
        ),
        CarAsset(
            id = "celica_gt4",
            name = "Toyota Celica GT-Four WRC",
            brand = "Toyota",
            description = "3S-GTE, постоянный 4WD, раллийная легенда Карлоса Сайнса.",
            iconEmoji = "🏎️",
            costAura = 180000.0,
            clickMultiplierBonus = 1.1,
            passiveMultiplierBonus = 0.9,
            speedKmh = 245,
            horsepower = 242
        ),
        CarAsset(
            id = "skyline_r32",
            name = "Nissan Skyline GT-R R32 Годзилла",
            brand = "Nissan",
            description = "RB26DETT, полный привод ATTESA E-TS, разгромил всех на треках.",
            iconEmoji = "🏎️",
            costAura = 280000.0,
            clickMultiplierBonus = 1.25,
            passiveMultiplierBonus = 1.05,
            speedKmh = 260,
            horsepower = 280
        ),
        CarAsset(
            id = "skyline_r33",
            name = "Nissan Skyline GT-R R33 V-Spec",
            brand = "Nissan",
            description = "Длинная база, устойчивость на высоких скоростях, NISMO обвес.",
            iconEmoji = "🏎️",
            costAura = 260000.0,
            clickMultiplierBonus = 1.2,
            passiveMultiplierBonus = 1.0,
            speedKmh = 265,
            horsepower = 280
        ),
        CarAsset(
            id = "skyline_r34",
            name = "Nissan Skyline GT-R R34 V-Spec II",
            brand = "Nissan",
            description = "Цвет Bayside Blue, бортовой дисплей MFD, мечта каждого моггера.",
            iconEmoji = "⚡",
            costAura = 650000.0,
            clickMultiplierBonus = 1.7,
            passiveMultiplierBonus = 1.45,
            speedKmh = 290,
            horsepower = 280
        ),
        CarAsset(
            id = "gtr_r35",
            name = "Nissan GT-R R35 Nismo",
            brand = "Nissan",
            description = "VR38DETT битурбо, ланч-контроль за 2.7с, разрывает суперкары.",
            iconEmoji = "⚡",
            costAura = 850000.0,
            clickMultiplierBonus = 1.9,
            passiveMultiplierBonus = 1.65,
            speedKmh = 330,
            horsepower = 600
        ),
        CarAsset(
            id = "silvia_s13",
            name = "Nissan Silvia S13 Club",
            brand = "Nissan",
            description = "CA18DET, легкий кузов, идеальная развесовка для дрифта.",
            iconEmoji = "🏎️",
            costAura = 140000.0,
            clickMultiplierBonus = 1.0,
            passiveMultiplierBonus = 0.8,
            speedKmh = 220,
            horsepower = 175
        ),
        CarAsset(
            id = "silvia_s14",
            name = "Nissan Silvia S14 Kouki",
            brand = "Nissan",
            description = "Агрессивная морда Kouki, SR20DET Blacktop, угол на треке.",
            iconEmoji = "🏎️",
            costAura = 170000.0,
            clickMultiplierBonus = 1.08,
            passiveMultiplierBonus = 0.88,
            speedKmh = 235,
            horsepower = 220
        ),
        CarAsset(
            id = "silvia_s15",
            name = "Nissan Silvia S15 Spec-R",
            brand = "Nissan",
            description = "6-ступенчатая механика, винтовая блокировка дифференциала.",
            iconEmoji = "🏎️",
            costAura = 240000.0,
            clickMultiplierBonus = 1.18,
            passiveMultiplierBonus = 0.98,
            speedKmh = 250,
            horsepower = 250
        ),
        CarAsset(
            id = "nissan_180sx",
            name = "Nissan 180SX Type X",
            brand = "Nissan",
            description = "Слепые фары, задний спойлер Type X, свист турбины T28.",
            iconEmoji = "🏎️",
            costAura = 160000.0,
            clickMultiplierBonus = 1.05,
            passiveMultiplierBonus = 0.85,
            speedKmh = 230,
            horsepower = 205
        ),
        CarAsset(
            id = "fairlady_350z",
            name = "Nissan 350Z Nismo",
            brand = "Nissan",
            description = "VQ35DE 3.5 V6, басовитый выхлоп, легенда Tokyo Drift.",
            iconEmoji = "🏎️",
            costAura = 190000.0,
            clickMultiplierBonus = 1.12,
            passiveMultiplierBonus = 0.92,
            speedKmh = 250,
            horsepower = 306
        ),
        CarAsset(
            id = "fairlady_370z",
            name = "Nissan 370Z Nismo",
            brand = "Nissan",
            description = "SynchroRev Match, 3.7 V6, жесткие тормоза Akebono.",
            iconEmoji = "🏎️",
            costAura = 250000.0,
            clickMultiplierBonus = 1.2,
            passiveMultiplierBonus = 1.0,
            speedKmh = 260,
            horsepower = 350
        ),
        CarAsset(
            id = "mazda_rx7_fd",
            name = "Mazda RX-7 FD3S Spirit R",
            brand = "Mazda",
            description = "13B-REW двухсекционный ротор, идеальные формы кузова.",
            iconEmoji = "🏎️",
            costAura = 380000.0,
            clickMultiplierBonus = 1.4,
            passiveMultiplierBonus = 1.2,
            speedKmh = 265,
            horsepower = 280
        ),
        CarAsset(
            id = "mazda_rx8",
            name = "Mazda RX-8 Renesis",
            brand = "Mazda",
            description = "Двери распашонки, отсечка на 9000 об/мин, запах касторового масла.",
            iconEmoji = "🚗",
            costAura = 95000.0,
            clickMultiplierBonus = 0.85,
            passiveMultiplierBonus = 0.7,
            speedKmh = 235,
            horsepower = 231
        ),
        CarAsset(
            id = "mazda_miata",
            name = "Mazda MX-5 Miata NA",
            brand = "Mazda",
            description = "Слепые фары подмигивают, легкий родстер, чистое удовольствие.",
            iconEmoji = "🏎️",
            costAura = 110000.0,
            clickMultiplierBonus = 0.9,
            passiveMultiplierBonus = 0.75,
            speedKmh = 205,
            horsepower = 131
        ),
        CarAsset(
            id = "subaru_22b",
            name = "Subaru Impreza 22B STI",
            brand = "Subaru",
            description = "Бу-бу-бу выхлоп, цвет World Rally Blue, золотые диски BBS.",
            iconEmoji = "🏎️",
            costAura = 550000.0,
            clickMultiplierBonus = 1.6,
            passiveMultiplierBonus = 1.35,
            speedKmh = 260,
            horsepower = 280
        ),
        CarAsset(
            id = "subaru_wrx_sti",
            name = "Subaru WRX STI S209",
            brand = "Subaru",
            description = "EJ257 с кованой поршневой, антикрыло, раллийный снаряд.",
            iconEmoji = "🏎️",
            costAura = 320000.0,
            clickMultiplierBonus = 1.3,
            passiveMultiplierBonus = 1.1,
            speedKmh = 265,
            horsepower = 341
        ),
        CarAsset(
            id = "subaru_forester_sti",
            name = "Subaru Forester STI SG9",
            brand = "Subaru",
            description = "Турбовый лесник на механике Brembo, волк в овечьей шкуре.",
            iconEmoji = "🚙",
            costAura = 180000.0,
            clickMultiplierBonus = 1.1,
            passiveMultiplierBonus = 0.9,
            speedKmh = 240,
            horsepower = 265
        ),
        CarAsset(
            id = "lancer_evo_6",
            name = "Mitsubishi Lancer Evolution VI T.M.E.",
            brand = "Mitsubishi",
            description = "Tommi Makinen Edition, титановая крыльчатка турбины.",
            iconEmoji = "🏎️",
            costAura = 420000.0,
            clickMultiplierBonus = 1.45,
            passiveMultiplierBonus = 1.25,
            speedKmh = 255,
            horsepower = 280
        ),
        CarAsset(
            id = "lancer_evo_9",
            name = "Mitsubishi Lancer Evolution IX MR",
            brand = "Mitsubishi",
            description = "4G63 MIVEC, карбоновое антикрыло, Bilstein подвеска.",
            iconEmoji = "🏎️",
            costAura = 360000.0,
            clickMultiplierBonus = 1.38,
            passiveMultiplierBonus = 1.18,
            speedKmh = 260,
            horsepower = 280
        ),
        CarAsset(
            id = "lancer_evo_10",
            name = "Mitsubishi Lancer Evolution X Final",
            brand = "Mitsubishi",
            description = "4B11T, полный привод S-AWC, агрессивная пасть акулы.",
            iconEmoji = "🏎️",
            costAura = 300000.0,
            clickMultiplierBonus = 1.28,
            passiveMultiplierBonus = 1.08,
            speedKmh = 255,
            horsepower = 303
        ),
        CarAsset(
            id = "civic_type_r_ek9",
            name = "Honda Civic Type R EK9",
            brand = "Honda",
            description = "B16B VTEC включается на 5800, красная зона до 8500 об/мин.",
            iconEmoji = "🏎️",
            costAura = 160000.0,
            clickMultiplierBonus = 1.05,
            passiveMultiplierBonus = 0.85,
            speedKmh = 225,
            horsepower = 185
        ),
        CarAsset(
            id = "integra_dc2",
            name = "Honda Integra Type R DC2",
            brand = "Honda",
            description = "Лучший переднеприводный спорткар 90-х, дифференциал Torsen.",
            iconEmoji = "🏎️",
            costAura = 190000.0,
            clickMultiplierBonus = 1.12,
            passiveMultiplierBonus = 0.92,
            speedKmh = 235,
            horsepower = 197
        ),
        CarAsset(
            id = "honda_s2000",
            name = "Honda S2000 AP2",
            brand = "Honda",
            description = "F22C1 атмосферник 120 л.с. с литра, развесовка 50:50, родстер.",
            iconEmoji = "🏎️",
            costAura = 280000.0,
            clickMultiplierBonus = 1.25,
            passiveMultiplierBonus = 1.05,
            speedKmh = 250,
            horsepower = 242
        ),
        CarAsset(
            id = "honda_nsx",
            name = "Honda NSX-R NA2",
            brand = "Honda",
            description = "Алюминиевый монокок, доводка Айртона Сенны, японский Ferrari.",
            iconEmoji = "⚡",
            costAura = 750000.0,
            clickMultiplierBonus = 1.8,
            passiveMultiplierBonus = 1.55,
            speedKmh = 280,
            horsepower = 290
        ),
        CarAsset(
            id = "bmw_m3_e30",
            name = "BMW M3 E30 Sport Evolution",
            brand = "BMW M",
            description = "Расширенные крылья, гоночный мотор S14, культ эпохи DTM.",
            iconEmoji = "🏎️",
            costAura = 450000.0,
            clickMultiplierBonus = 1.5,
            passiveMultiplierBonus = 1.3,
            speedKmh = 250,
            horsepower = 238
        ),
        CarAsset(
            id = "bmw_m3_e36",
            name = "BMW M3 E36 Coupe",
            brand = "BMW M",
            description = "Рядная шестерка S50B32, дроссели на каждый цилиндр, баварский стиль.",
            iconEmoji = "🏎️",
            costAura = 220000.0,
            clickMultiplierBonus = 1.15,
            passiveMultiplierBonus = 0.95,
            speedKmh = 250,
            horsepower = 321
        ),
        CarAsset(
            id = "bmw_m3_e46",
            name = "BMW M3 E46 CSL",
            brand = "BMW M",
            description = "Карбоновая крыша, мотор S54, рев впускного ресивера на Нюрбургринге.",
            iconEmoji = "🏎️",
            costAura = 480000.0,
            clickMultiplierBonus = 1.55,
            passiveMultiplierBonus = 1.32,
            speedKmh = 280,
            horsepower = 360
        ),
        CarAsset(
            id = "bmw_m3_e92",
            name = "BMW M3 E92 GTS",
            brand = "BMW M",
            description = "Атмосферный V8 S65 4.4L, каркас безопасности, оранжевый цвет.",
            iconEmoji = "🏎️",
            costAura = 550000.0,
            clickMultiplierBonus = 1.62,
            passiveMultiplierBonus = 1.38,
            speedKmh = 305,
            horsepower = 450
        ),
        CarAsset(
            id = "bmw_m3_g80",
            name = "BMW M3 Competition G80",
            brand = "BMW M",
            description = "Огромные ноздри, S58 Twin-Turbo, xDrive, разгон до 100 за 3.5с.",
            iconEmoji = "🏎️",
            costAura = 700000.0,
            clickMultiplierBonus = 1.75,
            passiveMultiplierBonus = 1.5,
            speedKmh = 290,
            horsepower = 510
        ),
        CarAsset(
            id = "bmw_m5_e34",
            name = "BMW M5 E34 Ручная сборка",
            brand = "BMW M",
            description = "Ручная сборка в Гархинге, 3.8 S38B38, классический бумер.",
            iconEmoji = "🏎️",
            costAura = 280000.0,
            clickMultiplierBonus = 1.25,
            passiveMultiplierBonus = 1.05,
            speedKmh = 250,
            horsepower = 340
        ),
        CarAsset(
            id = "bmw_m5_e39",
            name = "BMW M5 E39 V8 Шедевр",
            brand = "BMW M",
            description = "S62 5.0 V8, механическая КПП, эталон бизнес-спорт седана.",
            iconEmoji = "🏎️",
            costAura = 380000.0,
            clickMultiplierBonus = 1.4,
            passiveMultiplierBonus = 1.2,
            speedKmh = 250,
            horsepower = 400
        ),
        CarAsset(
            id = "bmw_m5_e60",
            name = "BMW M5 E60 V10 F1 Sound",
            brand = "BMW M",
            description = "S85 V10 кричит до 8250 об/мин, звук болида F1 на улицах.",
            iconEmoji = "⚡",
            costAura = 490000.0,
            clickMultiplierBonus = 1.56,
            passiveMultiplierBonus = 1.34,
            speedKmh = 305,
            horsepower = 507
        ),
        CarAsset(
            id = "bmw_m5_f10",
            name = "BMW M5 F10 30 Jahre",
            brand = "BMW M",
            description = "4.4 V8 TwinPower Turbo, 600 сил, лимитированная серия юбилея.",
            iconEmoji = "🏎️",
            costAura = 580000.0,
            clickMultiplierBonus = 1.65,
            passiveMultiplierBonus = 1.4,
            speedKmh = 305,
            horsepower = 600
        ),
        CarAsset(
            id = "bmw_m5_f90_cs",
            name = "BMW M5 CS F90 Облегченная",
            brand = "BMW M",
            description = "Карбоновый капот, желтые лазерные фары, 635 сил, 2.9с до 100.",
            iconEmoji = "⚡",
            costAura = 950000.0,
            clickMultiplierBonus = 2.0,
            passiveMultiplierBonus = 1.75,
            speedKmh = 305,
            horsepower = 635
        ),
        CarAsset(
            id = "bmw_m8_gc",
            name = "BMW M8 Gran Coupe Competition",
            brand = "BMW M",
            description = "Роскошный четырехдверный монстр, керамика, премиум комфорт.",
            iconEmoji = "👑",
            costAura = 1100000.0,
            clickMultiplierBonus = 2.1,
            passiveMultiplierBonus = 1.85,
            speedKmh = 305,
            horsepower = 625
        ),
        CarAsset(
            id = "bmw_x5m_f95",
            name = "BMW X5 M Competition F95",
            brand = "BMW M",
            description = "Спортивный кроссовер, разрывает светофоры города под громкий рык.",
            iconEmoji = "🚙",
            costAura = 900000.0,
            clickMultiplierBonus = 1.95,
            passiveMultiplierBonus = 1.7,
            speedKmh = 290,
            horsepower = 625
        ),
        CarAsset(
            id = "merc_190e_evo2",
            name = "Mercedes 190E 2.5-16 Evo II",
            brand = "Mercedes-Benz",
            description = "Гигантское антикрыло, расширители арок, кованые поршни Cosworth.",
            iconEmoji = "🏎️",
            costAura = 520000.0,
            clickMultiplierBonus = 1.58,
            passiveMultiplierBonus = 1.35,
            speedKmh = 250,
            horsepower = 235
        ),
        CarAsset(
            id = "merc_w124_wolf",
            name = "Mercedes-Benz 500E W124 Волчок",
            brand = "Mercedes-Benz",
            description = "Сборка на заводе Porsche, V8 M119, легенда бандитских 90-х.",
            iconEmoji = "🚗",
            costAura = 390000.0,
            clickMultiplierBonus = 1.42,
            passiveMultiplierBonus = 1.22,
            speedKmh = 250,
            horsepower = 326
        ),
        CarAsset(
            id = "merc_w140_boar",
            name = "Mercedes-Benz S600 W140 Кабан V12",
            brand = "Mercedes-Benz",
            description = "Шестилитровый V12 M120, двойные стеклопакеты, абсолютная власть.",
            iconEmoji = "👑",
            costAura = 450000.0,
            clickMultiplierBonus = 1.5,
            passiveMultiplierBonus = 1.3,
            speedKmh = 250,
            horsepower = 394
        ),
        CarAsset(
            id = "merc_w221_s63",
            name = "Mercedes-Benz S63 AMG W221",
            brand = "Mercedes-Benz",
            description = "6.2 атмосферный V8 M156, выхлоп с громогласным басом.",
            iconEmoji = "👑",
            costAura = 550000.0,
            clickMultiplierBonus = 1.62,
            passiveMultiplierBonus = 1.38,
            speedKmh = 250,
            horsepower = 525
        ),
        CarAsset(
            id = "merc_w222_maybach",
            name = "Mercedes-Maybach S650 W222 V12",
            brand = "Mercedes-Benz",
            description = "Раздельные кресла-оттоманки, бокалы с серебром, полет первого класса.",
            iconEmoji = "👑",
            costAura = 1400000.0,
            clickMultiplierBonus = 2.25,
            passiveMultiplierBonus = 2.05,
            speedKmh = 250,
            horsepower = 630
        ),
        CarAsset(
            id = "merc_w223_amg",
            name = "Mercedes-AMG S63 E Performance W223",
            brand = "Mercedes-Benz",
            description = "Гибридный V8 битурбо, 802 силы и 1430 Нм крутящего момента!",
            iconEmoji = "⚡",
            costAura = 1800000.0,
            clickMultiplierBonus = 2.45,
            passiveMultiplierBonus = 2.25,
            speedKmh = 290,
            horsepower = 802
        ),
        CarAsset(
            id = "merc_c63_w204",
            name = "Mercedes-Benz C63 AMG W204 Black Series",
            brand = "Mercedes-Benz",
            description = "Атмосферный 6.2 V8, сжигает заднюю резину до корда.",
            iconEmoji = "🏎️",
            costAura = 650000.0,
            clickMultiplierBonus = 1.7,
            passiveMultiplierBonus = 1.45,
            speedKmh = 300,
            horsepower = 517
        ),
        CarAsset(
            id = "merc_e63s_w213",
            name = "Mercedes-AMG E63s 4MATIC+ W213",
            brand = "Mercedes-Benz",
            description = "Режим дрифта, отключение передка, 612 сил и старт за 3.3с.",
            iconEmoji = "🏎️",
            costAura = 850000.0,
            clickMultiplierBonus = 1.9,
            passiveMultiplierBonus = 1.65,
            speedKmh = 300,
            horsepower = 612
        ),
        CarAsset(
            id = "merc_cls63_banana",
            name = "Mercedes-Benz CLS 63 AMG Банан",
            brand = "Mercedes-Benz",
            description = "Форма кузова купе-седан, суровый взгляд, любимец стритрейсеров.",
            iconEmoji = "🏎️",
            costAura = 600000.0,
            clickMultiplierBonus = 1.68,
            passiveMultiplierBonus = 1.42,
            speedKmh = 300,
            horsepower = 557
        ),
        CarAsset(
            id = "merc_g63_brabus",
            name = "Brabus 800 Widestar Гелик",
            brand = "Mercedes-Benz",
            description = "800 сил, карбоновый капот, светящаяся решетка, полный страх в потоке.",
            iconEmoji = "🚙",
            costAura = 1600000.0,
            clickMultiplierBonus = 2.35,
            passiveMultiplierBonus = 2.15,
            speedKmh = 240,
            horsepower = 800
        ),
        CarAsset(
            id = "merc_g63_6x6",
            name = "Mercedes-Benz G63 AMG 6x6",
            brand = "Mercedes-Benz",
            description = "Шестиколесный портальный монстр, подкачка колес, покоритель дюн.",
            iconEmoji = "🚛",
            costAura = 2200000.0,
            clickMultiplierBonus = 2.6,
            passiveMultiplierBonus = 2.4,
            speedKmh = 160,
            horsepower = 544
        ),
        CarAsset(
            id = "merc_sls_amg",
            name = "Mercedes-Benz SLS AMG Крыло Чайки",
            brand = "Mercedes-Benz",
            description = "Двери открываются вверх, длинный капот, 6.2 V8 M159.",
            iconEmoji = "🏎️",
            costAura = 1200000.0,
            clickMultiplierBonus = 2.15,
            passiveMultiplierBonus = 1.9,
            speedKmh = 317,
            horsepower = 571
        ),
        CarAsset(
            id = "merc_amg_gt_black",
            name = "Mercedes-AMG GT Black Series",
            brand = "Mercedes-Benz",
            description = "Плоский коленвал, активная аэродинамика, рекордсмен Нюрбургринга.",
            iconEmoji = "⚡",
            costAura = 1500000.0,
            clickMultiplierBonus = 2.3,
            passiveMultiplierBonus = 2.1,
            speedKmh = 325,
            horsepower = 730
        ),
        CarAsset(
            id = "audi_rs4_b7",
            name = "Audi RS4 B7 4.2 V8 МКПП",
            brand = "Audi",
            description = "Атмосферный высокооборотистый V8, полный привод Quattro, механика.",
            iconEmoji = "🏎️",
            costAura = 320000.0,
            clickMultiplierBonus = 1.3,
            passiveMultiplierBonus = 1.1,
            speedKmh = 250,
            horsepower = 420
        ),
        CarAsset(
            id = "audi_rs6_c6",
            name = "Audi RS6 C6 V10 Twin-Turbo",
            brand = "Audi",
            description = "Мотор V10 от Lamborghini с двумя турбинами, 580 сил в кузове универсал.",
            iconEmoji = "🏎️",
            costAura = 450000.0,
            clickMultiplierBonus = 1.5,
            passiveMultiplierBonus = 1.3,
            speedKmh = 280,
            horsepower = 580
        ),
        CarAsset(
            id = "audi_rs6_c8",
            name = "Audi RS6 Avant C8 ABT 740",
            brand = "Audi",
            description = "Широкий кузов, злой взгляд, карбоновый обвес ABT, 740 сил.",
            iconEmoji = "🏎️",
            costAura = 1100000.0,
            clickMultiplierBonus = 2.1,
            passiveMultiplierBonus = 1.85,
            speedKmh = 315,
            horsepower = 740
        ),
        CarAsset(
            id = "audi_rs7_sportback",
            name = "Audi RS7 Sportback Performance",
            brand = "Audi",
            description = "Обтекаемый лифтбек, пневмоподвеска, стремительный силуэт.",
            iconEmoji = "🏎️",
            costAura = 980000.0,
            clickMultiplierBonus = 2.02,
            passiveMultiplierBonus = 1.78,
            speedKmh = 305,
            horsepower = 630
        ),
        CarAsset(
            id = "audi_r8_v10",
            name = "Audi R8 V10 Plus Performance",
            brand = "Audi",
            description = "5.2 V10 за спиной поет на 8700 об/мин, лазерные фары.",
            iconEmoji = "⚡",
            costAura = 1300000.0,
            clickMultiplierBonus = 2.2,
            passiveMultiplierBonus = 1.95,
            speedKmh = 330,
            horsepower = 620
        ),
        CarAsset(
            id = "porsche_911_gt3",
            name = "Porsche 911 992 GT3 RS",
            brand = "Porsche",
            description = "Аэродинамика DRS, двухрычажка спереди, филигранная точность в поворотах.",
            iconEmoji = "🏁",
            costAura = 1450000.0,
            clickMultiplierBonus = 2.28,
            passiveMultiplierBonus = 2.08,
            speedKmh = 296,
            horsepower = 525
        ),
        CarAsset(
            id = "porsche_911_turbo",
            name = "Porsche 911 992 Turbo S",
            brand = "Porsche",
            description = "Разгон до 100 за 2.5с при любой погоде, совершенство немецкой инженерии.",
            iconEmoji = "⚡",
            costAura = 1400000.0,
            clickMultiplierBonus = 2.25,
            passiveMultiplierBonus = 2.05,
            speedKmh = 330,
            horsepower = 650
        ),
        CarAsset(
            id = "porsche_carrera_gt",
            name = "Porsche Carrera GT V10",
            brand = "Porsche",
            description = "Керамическое сцепление, атмосферный V10, звук дикого зверя.",
            iconEmoji = "⚡",
            costAura = 2800000.0,
            clickMultiplierBonus = 2.8,
            passiveMultiplierBonus = 2.6,
            speedKmh = 330,
            horsepower = 612
        ),
        CarAsset(
            id = "porsche_918_spyder",
            name = "Porsche 918 Spyder Weissach",
            brand = "Porsche",
            description = "Гибридный гиперкар, выхлопные трубы вверх за головой, 887 сил.",
            iconEmoji = "⚡",
            costAura = 3500000.0,
            clickMultiplierBonus = 3.1,
            passiveMultiplierBonus = 2.9,
            speedKmh = 345,
            horsepower = 887
        ),
        CarAsset(
            id = "porsche_cayman_gt4",
            name = "Porsche 718 Cayman GT4 RS",
            brand = "Porsche",
            description = "Воздухозаборники вместо боковых стекол, звук прямо в уши водителя.",
            iconEmoji = "🏁",
            costAura = 890000.0,
            clickMultiplierBonus = 1.94,
            passiveMultiplierBonus = 1.68,
            speedKmh = 315,
            horsepower = 500
        ),
        CarAsset(
            id = "lambo_countach",
            name = "Lamborghini Countach LP5000 QV",
            brand = "Lamborghini",
            description = "Двери-ножницы, клиновидный дизайн Марчелло Гандини, икона 80-х.",
            iconEmoji = "⚡",
            costAura = 1200000.0,
            clickMultiplierBonus = 2.15,
            passiveMultiplierBonus = 1.9,
            speedKmh = 295,
            horsepower = 455
        ),
        CarAsset(
            id = "lambo_diablo",
            name = "Lamborghini Diablo VT 6.0",
            brand = "Lamborghini",
            description = "Полный привод, карбоновый салон, 6.0 V12 ревет на всю округу.",
            iconEmoji = "⚡",
            costAura = 1400000.0,
            clickMultiplierBonus = 2.25,
            passiveMultiplierBonus = 2.05,
            speedKmh = 335,
            horsepower = 550
        ),
        CarAsset(
            id = "lambo_murcielago",
            name = "Lamborghini Murcielago LP670-4 SV",
            brand = "Lamborghini",
            description = "Супервеличественный SV, прозрачная крышка V12, антикрыло Aeropack.",
            iconEmoji = "⚡",
            costAura = 1800000.0,
            clickMultiplierBonus = 2.45,
            passiveMultiplierBonus = 2.25,
            speedKmh = 342,
            horsepower = 670
        ),
        CarAsset(
            id = "lambo_huracan_sto",
            name = "Lamborghini Huracan STO",
            brand = "Lamborghini",
            description = "Трековый снаряд с допуском на дороги, задний привод, дикий V10.",
            iconEmoji = "🏁",
            costAura = 1650000.0,
            clickMultiplierBonus = 2.38,
            passiveMultiplierBonus = 2.18,
            speedKmh = 310,
            horsepower = 640
        ),
        CarAsset(
            id = "lambo_aventador_svj",
            name = "Lamborghini Aventador SVJ",
            brand = "Lamborghini",
            description = "Система активной аэродинамики ALA 2.0, 770 сил чистого яда.",
            iconEmoji = "⚡",
            costAura = 2200000.0,
            clickMultiplierBonus = 2.6,
            passiveMultiplierBonus = 2.4,
            speedKmh = 351,
            horsepower = 770
        ),
        CarAsset(
            id = "lambo_revuelto",
            name = "Lamborghini Revuelto V12 Hybrid",
            brand = "Lamborghini",
            description = "1015 л.с., новый флагман из Сант-Агаты, три электромотора.",
            iconEmoji = "⚡",
            costAura = 2900000.0,
            clickMultiplierBonus = 2.85,
            passiveMultiplierBonus = 2.65,
            speedKmh = 350,
            horsepower = 1015
        ),
        CarAsset(
            id = "lambo_urus_mansory",
            name = "Lamborghini Urus Mansory Venatus",
            brand = "Lamborghini",
            description = "Ультра-агрессивный карбоновый кузов, 810 сил, король суеты.",
            iconEmoji = "🚙",
            costAura = 1750000.0,
            clickMultiplierBonus = 2.42,
            passiveMultiplierBonus = 2.22,
            speedKmh = 320,
            horsepower = 810
        ),
        CarAsset(
            id = "ferrari_testarossa",
            name = "Ferrari Testarossa",
            brand = "Ferrari",
            description = "Знаменитые ребра на бортах, 12-цилиндровый оппозит, стиль Майами.",
            iconEmoji = "🏎️",
            costAura = 950000.0,
            clickMultiplierBonus = 2.0,
            passiveMultiplierBonus = 1.75,
            speedKmh = 290,
            horsepower = 390
        ),
        CarAsset(
            id = "ferrari_f40",
            name = "Ferrari F40 Твин-Турбо",
            brand = "Ferrari",
            description = "Последний шедевр Энцо Феррари, спартанский карбоновый салон, 478 сил.",
            iconEmoji = "⚡",
            costAura = 3200000.0,
            clickMultiplierBonus = 3.0,
            passiveMultiplierBonus = 2.8,
            speedKmh = 324,
            horsepower = 478
        ),
        CarAsset(
            id = "ferrari_f50",
            name = "Ferrari F50 С мотором F1",
            brand = "Ferrari",
            description = "Безнаддувный V12 прямо от болида Формулы-1, открытый кузов тарга.",
            iconEmoji = "⚡",
            costAura = 3600000.0,
            clickMultiplierBonus = 3.15,
            passiveMultiplierBonus = 2.95,
            speedKmh = 325,
            horsepower = 520
        ),
        CarAsset(
            id = "ferrari_enzo",
            name = "Ferrari Enzo",
            brand = "Ferrari",
            description = "Нос в стиле болида F1, 6.0 V12 660 сил, вершина эпохи Михаэля Шумахера.",
            iconEmoji = "⚡",
            costAura = 4200000.0,
            clickMultiplierBonus = 3.3,
            passiveMultiplierBonus = 3.1,
            speedKmh = 350,
            horsepower = 660
        ),
        CarAsset(
            id = "ferrari_458_speciale",
            name = "Ferrari 458 Speciale",
            brand = "Ferrari",
            description = "Последний атмосферный V8 Ferrari, крутится до 9000 об/мин, звук магии.",
            iconEmoji = "🏎️",
            costAura = 1350000.0,
            clickMultiplierBonus = 2.22,
            passiveMultiplierBonus = 2.02,
            speedKmh = 325,
            horsepower = 605
        ),
        CarAsset(
            id = "ferrari_488_pista",
            name = "Ferrari 488 Pista",
            brand = "Ferrari",
            description = "S-Duct аэродинамика на носу, 720 сил твин-турбо, хирургический скальпель.",
            iconEmoji = "🏎️",
            costAura = 1550000.0,
            clickMultiplierBonus = 2.32,
            passiveMultiplierBonus = 2.12,
            speedKmh = 340,
            horsepower = 720
        ),
        CarAsset(
            id = "ferrari_sf90",
            name = "Ferrari SF90 Stradale Assetto Fiorano",
            brand = "Ferrari",
            description = "1000 л.с., разгон до сотни за 2.2 секунды, гибридная ракета.",
            iconEmoji = "⚡",
            costAura = 2400000.0,
            clickMultiplierBonus = 2.68,
            passiveMultiplierBonus = 2.48,
            speedKmh = 340,
            horsepower = 1000
        ),
        CarAsset(
            id = "ferrari_laferrari",
            name = "LaFerrari Aperta",
            brand = "Ferrari",
            description = "Гибридный гиперкар Святой Троицы, 963 силы, невероятные линии кузова.",
            iconEmoji = "👑",
            costAura = 5000000.0,
            clickMultiplierBonus = 3.5,
            passiveMultiplierBonus = 3.3,
            speedKmh = 350,
            horsepower = 963
        ),
        CarAsset(
            id = "mclaren_f1",
            name = "McLaren F1 Позолоченный мотор",
            brand = "McLaren",
            description = "Водитель по центру, моторный отсек покрыт чистым золотом, рекорд 391 км/ч.",
            iconEmoji = "👑",
            costAura = 6500000.0,
            clickMultiplierBonus = 3.8,
            passiveMultiplierBonus = 3.6,
            speedKmh = 391,
            horsepower = 627
        ),
        CarAsset(
            id = "mclaren_720s",
            name = "McLaren 720S Performance",
            brand = "McLaren",
            description = "Карбоновый кокпит Monocage II, фары-воздуховоды, ураганный разгон.",
            iconEmoji = "🏎️",
            costAura = 1250000.0,
            clickMultiplierBonus = 2.18,
            passiveMultiplierBonus = 1.92,
            speedKmh = 341,
            horsepower = 720
        ),
        CarAsset(
            id = "mclaren_765lt",
            name = "McLaren 765LT Длинный хвост",
            brand = "McLaren",
            description = "Титановый четырехствольный выхлоп плюется синим огнем, 765 сил.",
            iconEmoji = "⚡",
            costAura = 1700000.0,
            clickMultiplierBonus = 2.4,
            passiveMultiplierBonus = 2.2,
            speedKmh = 330,
            horsepower = 765
        ),
        CarAsset(
            id = "mclaren_p1",
            name = "McLaren P1 Гиперкар",
            brand = "McLaren",
            description = "Гоночный режим с опусканием клиренса на 50 мм, 916 сил, пламя из выхлопа.",
            iconEmoji = "⚡",
            costAura = 3800000.0,
            clickMultiplierBonus = 3.2,
            passiveMultiplierBonus = 3.0,
            speedKmh = 350,
            horsepower = 916
        ),
        CarAsset(
            id = "bugatti_veyron_ss",
            name = "Bugatti Veyron 16.4 Super Sport",
            brand = "Bugatti",
            description = "1200 сил, 4 турбины, первый дорожный автомобиль перешагнувший 431 км/ч.",
            iconEmoji = "👑",
            costAura = 4500000.0,
            clickMultiplierBonus = 3.4,
            passiveMultiplierBonus = 3.2,
            speedKmh = 431,
            horsepower = 1200
        ),
        CarAsset(
            id = "bugatti_chiron_ss",
            name = "Bugatti Chiron Super Sport 300+",
            brand = "Bugatti",
            description = "Преодолел барьер в 300 миль/ч (490 км/ч), удлиненный кузов Longtail.",
            iconEmoji = "⚡",
            costAura = 8000000.0,
            clickMultiplierBonus = 4.2,
            passiveMultiplierBonus = 4.0,
            speedKmh = 490,
            horsepower = 1600
        ),
        CarAsset(
            id = "bugatti_divo",
            name = "Bugatti Divo Ограниченная серия",
            brand = "Bugatti",
            description = "Всего 40 штук в мире, повышенная прижимная сила для горных серпантинов.",
            iconEmoji = "👑",
            costAura = 9500000.0,
            clickMultiplierBonus = 4.5,
            passiveMultiplierBonus = 4.3,
            speedKmh = 380,
            horsepower = 1500
        ),
        CarAsset(
            id = "pagani_zonda_r",
            name = "Pagani Zonda R Evolution",
            brand = "Pagani",
            description = "Карбон-титановый монокок, формульный AMG V12, произведение искусства.",
            iconEmoji = "🏆",
            costAura = 7000000.0,
            clickMultiplierBonus = 4.0,
            passiveMultiplierBonus = 3.8,
            speedKmh = 350,
            horsepower = 800
        ),
        CarAsset(
            id = "pagani_huayra_bc",
            name = "Pagani Huayra BC",
            brand = "Pagani",
            description = "Активные закрылки аэродинамики, салон как в швейцарских часах.",
            iconEmoji = "👑",
            costAura = 6000000.0,
            clickMultiplierBonus = 3.7,
            passiveMultiplierBonus = 3.5,
            speedKmh = 370,
            horsepower = 789
        ),
        CarAsset(
            id = "koenigsegg_jesko",
            name = "Koenigsegg Jesko Attack",
            brand = "Koenigsegg",
            description = "1600 сил на биотопливе E85, трансмиссия Light Speed 9 сцеплений!",
            iconEmoji = "⚡",
            costAura = 10000000.0,
            clickMultiplierBonus = 5.0,
            passiveMultiplierBonus = 4.8,
            speedKmh = 480,
            horsepower = 1600
        ),
        CarAsset(
            id = "shelby_gt500",
            name = "Ford Mustang Shelby GT500 Predator",
            brand = "Ford",
            description = "5.2 V8 с компрессором Eaton, 760 сил, змея на решетке радиатора.",
            iconEmoji = "🏎️",
            costAura = 680000.0,
            clickMultiplierBonus = 1.72,
            passiveMultiplierBonus = 1.48,
            speedKmh = 290,
            horsepower = 760
        ),
        CarAsset(
            id = "corvette_c7_z06",
            name = "Chevrolet Corvette C7 Z06",
            brand = "Chevrolet",
            description = "Съемная крыша, компрессор LT4 650 сил, рев американского V8.",
            iconEmoji = "🏎️",
            costAura = 580000.0,
            clickMultiplierBonus = 1.65,
            passiveMultiplierBonus = 1.4,
            speedKmh = 315,
            horsepower = 650
        ),
        CarAsset(
            id = "corvette_c8_z06",
            name = "Chevrolet Corvette C8 Z06 Плоский вал",
            brand = "Chevrolet",
            description = "Среднемоторный V8 5.5L LT6 крутится до 8600 об/мин, звук гиперкара.",
            iconEmoji = "🏎️",
            costAura = 820000.0,
            clickMultiplierBonus = 1.88,
            passiveMultiplierBonus = 1.62,
            speedKmh = 314,
            horsepower = 670
        ),
        CarAsset(
            id = "dodge_demon_170",
            name = "Dodge Challenger SRT Demon 170",
            brand = "Dodge",
            description = "1025 сил, отрыв передних колес от асфальта на старте, 1.66с до 100!",
            iconEmoji = "⚡",
            costAura = 980000.0,
            clickMultiplierBonus = 2.05,
            passiveMultiplierBonus = 1.8,
            speedKmh = 346,
            horsepower = 1025
        ),
        CarAsset(
            id = "ram_1500_trx",
            name = "RAM 1500 TRX Мамонт 6.2 Hellcat",
            brand = "RAM",
            description = "702 силы в гигантском пикапе, прыжки по трамплинам в пустыне.",
            iconEmoji = "🚙",
            costAura = 780000.0,
            clickMultiplierBonus = 1.82,
            passiveMultiplierBonus = 1.58,
            speedKmh = 190,
            horsepower = 702
        ),
        CarAsset(
            id = "cybertruck_beast",
            name = "Tesla Cybertruck Cyberbeast",
            brand = "Tesla",
            description = "Пуленепробиваемый экзоскелет из нержавейки, 845 сил, разгон за 2.6с.",
            iconEmoji = "⚡",
            costAura = 890000.0,
            clickMultiplierBonus = 1.92,
            passiveMultiplierBonus = 1.68,
            speedKmh = 210,
            horsepower = 845
        ),
        CarAsset(
            id = "oka_turbo",
            name = "Турбо-Ока 500 л.с. «Бешеный Тапок»",
            brand = "Мем-Гараж",
            description = "Мотор от Hayabusa с турбиной Garrett, вес 500 кг, обгоняет суперкары.",
            iconEmoji = "🏎️",
            costAura = 200000.0,
            clickMultiplierBonus = 1.15,
            passiveMultiplierBonus = 0.95,
            speedKmh = 240,
            horsepower = 500
        ),
        CarAsset(
            id = "ashan_cart_v8",
            name = "Тележка из Ашана на V8",
            brand = "Мем-Гараж",
            description = "Колесики гремят, мотор Big Block 7.4L ревет, абсолютный моггинг парковки.",
            iconEmoji = "🛒",
            costAura = 300000.0,
            clickMultiplierBonus = 1.3,
            passiveMultiplierBonus = 1.1,
            speedKmh = 180,
            horsepower = 450
        ),
        CarAsset(
            id = "samokat_nos",
            name = "Электросамокат с закисью азота",
            brand = "Мем-Гараж",
            description = "100 км/ч по тротуару, баллон NOS на руле, седые волосы прохожих.",
            iconEmoji = "🛴",
            costAura = 80000.0,
            clickMultiplierBonus = 0.8,
            passiveMultiplierBonus = 0.65,
            speedKmh = 100,
            horsepower = 35
        ),
        CarAsset(
            id = "batin_zaporozhec",
            name = "Батин Запорожец Turbo 333",
            brand = "Мем-Гараж",
            description = "Спец-эдишн от Пенисова 333, сабвуфер на весь салон, золотые диски.",
            iconEmoji = "👑",
            costAura = 333333.0,
            clickMultiplierBonus = 1.45,
            passiveMultiplierBonus = 1.25,
            speedKmh = 210,
            horsepower = 333
        ),
    )

    val REAL_ESTATE = listOf(
        RealEstateAsset(
            id = "garage_gsk",
            name = "Гараж в ГСК «Автолюбитель»",
            location = "Промзона города",
            description = "Смотровая яма, верстак, банка соленых огурцов и убежище от суеты.",
            iconEmoji = "🏚️",
            costAura = 5000.0,
            passiveAuraPerSec = 50.0,
            auraMultiplierBonus = 0.05
        ),
        RealEstateAsset(
            id = "komnatka",
            name = "Комната в питерской коммуналке",
            location = "Санкт-Петербург, Петроградка",
            description = "Высокие потолки с лепниной, коммунальная кухня и разговоры об искусстве.",
            iconEmoji = "🚪",
            costAura = 10000.0,
            passiveAuraPerSec = 120.0,
            auraMultiplierBonus = 0.1
        ),
        RealEstateAsset(
            id = "khrushchevka",
            name = "Однушка в Хрущёвке",
            location = "Спальный район",
            description = "Ковер на стене, скрипучий паркет и турник в дверном проеме. Уютное логово.",
            iconEmoji = "🏚️",
            costAura = 25000.0,
            passiveAuraPerSec = 300.0,
            auraMultiplierBonus = 0.15
        ),
        RealEstateAsset(
            id = "dacha_berezka",
            name = "Дача в СНТ «Берёзка»",
            location = "Подмосковье, 45 км",
            description = "Участок 6 соток, мангал, банька на дровах и грядки с укропом.",
            iconEmoji = "🏡",
            costAura = 50000.0,
            passiveAuraPerSec = 700.0,
            auraMultiplierBonus = 0.25
        ),
        RealEstateAsset(
            id = "panelka_dvushka",
            name = "Двушка в Брежневке",
            location = "Район Чертаново",
            description = "Балкон застеклен старыми рамами, лыжи в углу, вид на вечерний двор.",
            iconEmoji = "🏢",
            costAura = 85000.0,
            passiveAuraPerSec = 1200.0,
            auraMultiplierBonus = 0.35
        ),
        RealEstateAsset(
            id = "stalinka",
            name = "Сталинка на Тверской",
            location = "Москва, Тверская ул.",
            description = "Потолки 3.8 метра, дубовый паркет, толстые стены и консьерж в подъезде.",
            iconEmoji = "🏛️",
            costAura = 200000.0,
            passiveAuraPerSec = 3000.0,
            auraMultiplierBonus = 0.55
        ),
        RealEstateAsset(
            id = "loft_zavod",
            name = "Лофт на Красном Октябре",
            location = "Москва, Берсеневская наб.",
            description = "Кирпичные стены XIX века, открытая вентиляция, арт-пространство сигмы.",
            iconEmoji = "🏭",
            costAura = 450000.0,
            passiveAuraPerSec = 7500.0,
            auraMultiplierBonus = 0.75
        ),
        RealEstateAsset(
            id = "moscow_city",
            name = "Апартаменты в Москва-Сити",
            location = "Пресненская наб., Федерация",
            description = "68 этаж, панорамные окна в пол, вид на всю Москву и миллионные сделки.",
            iconEmoji = "🏙️",
            costAura = 750000.0,
            passiveAuraPerSec = 14000.0,
            auraMultiplierBonus = 0.9
        ),
        RealEstateAsset(
            id = "patriarshie",
            name = "Пентхаус на Патриарших",
            location = "Патриаршие Пруды",
            description = "Эпицентр роскоши, модных кофеен и топовых луксмаксеров столицы.",
            iconEmoji = "🍸",
            costAura = 1500000.0,
            passiveAuraPerSec = 30000.0,
            auraMultiplierBonus = 1.2
        ),
        RealEstateAsset(
            id = "dom_derevnya",
            name = "Усадьба на Валдае",
            location = "Валдайские озера",
            description = "Бревенчатый сруб из сибирского кедра, чистейший воздух, русская баня.",
            iconEmoji = "🌲",
            costAura = 1200000.0,
            passiveAuraPerSec = 24000.0,
            auraMultiplierBonus = 1.1
        ),
        RealEstateAsset(
            id = "cottage_riga",
            name = "Коттедж на Новой Риге",
            location = "Миллениум Парк",
            description = "Закрытый элитный поселок, озера, вертолетная площадка, тишина.",
            iconEmoji = "🏰",
            costAura = 2500000.0,
            passiveAuraPerSec = 55000.0,
            auraMultiplierBonus = 1.5
        ),
        RealEstateAsset(
            id = "rublevka_mansion",
            name = "Особняк на Рублёвке",
            location = "Барвиха Luxury Village",
            description = "Золотые ворота, мраморные колонны, подземный гараж на 10 гиперкаров.",
            iconEmoji = "👑",
            costAura = 4000000.0,
            passiveAuraPerSec = 90000.0,
            auraMultiplierBonus = 1.8
        ),
        RealEstateAsset(
            id = "sochi_villa",
            name = "Вилла с видом на Чёрное море",
            location = "Сочи, Красная Поляна",
            description = "Панорамный инфинити-бассейн на скале, пальмы и снежные шапки гор.",
            iconEmoji = "🏖️",
            costAura = 3500000.0,
            passiveAuraPerSec = 80000.0,
            auraMultiplierBonus = 1.7
        ),
        RealEstateAsset(
            id = "dubai_marina",
            name = "Пентхаус в Dubai Marina",
            location = "Дубай, ОАЭ",
            description = "Вид на белоснежные яхты, золотой лифт, личный шеф-повар и кондиционер.",
            iconEmoji = "🏙️",
            costAura = 6000000.0,
            passiveAuraPerSec = 140000.0,
            auraMultiplierBonus = 2.2
        ),
        RealEstateAsset(
            id = "palm_jumeirah",
            name = "Вилла на Palm Jumeirah",
            location = "Дубай, Palm Jumeirah",
            description = "Собственный песчаный пляж у Персидского залива и золотой причал.",
            iconEmoji = "🌴",
            costAura = 8500000.0,
            passiveAuraPerSec = 200000.0,
            auraMultiplierBonus = 2.5
        ),
        RealEstateAsset(
            id = "burj_khalifa",
            name = "Этаж в Burj Khalifa",
            location = "Дубай, Downtown",
            description = "140 этаж, парение над облаками, абсолютная вершина мирового моггинга.",
            iconEmoji = "⚡",
            costAura = 12000000.0,
            passiveAuraPerSec = 300000.0,
            auraMultiplierBonus = 3.0
        ),
        RealEstateAsset(
            id = "monaco_penthouse",
            name = "Апартаменты в Монте-Карло",
            location = "Монако, Port Hercule",
            description = "Балкон прямо над стартовой решеткой Гран-При Формулы-1.",
            iconEmoji = "🏎️",
            costAura = 15000000.0,
            passiveAuraPerSec = 400000.0,
            auraMultiplierBonus = 3.4
        ),
        RealEstateAsset(
            id = "swiss_castle",
            name = "Замок в Швейцарских Альпах",
            location = "Санкт-Мориц, Швейцария",
            description = "Абсолютная цитадель Апекс Бога. Горные вершины и термальные спа.",
            iconEmoji = "🏰",
            costAura = 20000000.0,
            passiveAuraPerSec = 550000.0,
            auraMultiplierBonus = 3.8
        ),
        RealEstateAsset(
            id = "lake_como",
            name = "Вилла на озере Комо",
            location = "Италия, Lago di Como",
            description = "Кипарисовая аллея, классическая итальянская терраса, личный катер Riva.",
            iconEmoji = "🍇",
            costAura = 18000000.0,
            passiveAuraPerSec = 500000.0,
            auraMultiplierBonus = 3.6
        ),
        RealEstateAsset(
            id = "miami_beach",
            name = "Особняк в Майами Star Island",
            location = "Майами, Флорида",
            description = "Причал для 50-метровой яхты, неоновый свет и вечное солнце.",
            iconEmoji = "🍹",
            costAura = 25000000.0,
            passiveAuraPerSec = 700000.0,
            auraMultiplierBonus = 4.2
        ),
        RealEstateAsset(
            id = "manhattan_tower",
            name = "Пентхаус на 432 Park Avenue",
            location = "Нью-Йорк, Манхэттен",
            description = "Панорама на весь Центральный Парк с высоты птичьего полета.",
            iconEmoji = "🗽",
            costAura = 30000000.0,
            passiveAuraPerSec = 850000.0,
            auraMultiplierBonus = 4.6
        ),
        RealEstateAsset(
            id = "private_island",
            name = "Личный Остров на Мальдивах",
            location = "Индийский океан",
            description = "Бирюзовая лагуна, взлетно-посадочная полоса, независимое государство.",
            iconEmoji = "🏝️",
            costAura = 50000000.0,
            passiveAuraPerSec = 1500000.0,
            auraMultiplierBonus = 5.5
        ),
        RealEstateAsset(
            id = "bunker_sigma",
            name = "Подземный Бункер Апекс Сигмы",
            location = "Секретная локация в Сибири",
            description = "Автономность на 100 лет, серверные стойки для майнинга и бассейн.",
            iconEmoji = "🛡️",
            costAura = 40000000.0,
            passiveAuraPerSec = 1200000.0,
            auraMultiplierBonus = 5.0
        ),
        RealEstateAsset(
            id = "moon_base",
            name = "Лунная База «Моггер-1»",
            location = "Море Спокойствия, Луна",
            description = "Герметичный купол, пониженная гравитация для рекордных прыжков.",
            iconEmoji = "🌕",
            costAura = 100000000.0,
            passiveAuraPerSec = 3500000.0,
            auraMultiplierBonus = 7.5
        ),
        RealEstateAsset(
            id = "orbital_station",
            name = "Орбитальная Станция «Гигачад»",
            location = "Околоземная орбита",
            description = "Личный космический дворец, вид на всю планету Земля, высший статус.",
            iconEmoji = "🛸",
            costAura = 250000000.0,
            passiveAuraPerSec = 10000000.0,
            auraMultiplierBonus = 10.0
        ),
    )

    val INITIAL_CRYPTO = listOf(
        CryptoCoin("BTC", "Bitcoin", "🪙", 65000.0, 65000.0, 4.2, 0.0),
        CryptoCoin("ETH", "Ethereum", "🔷", 3500.0, 3500.0, -1.8, 0.0),
        CryptoCoin("TON", "Toncoin", "💎", 700.0, 700.0, 12.5, 0.0),
        CryptoCoin("SOL", "Solana", "🟣", 1600.0, 1600.0, 8.4, 0.0),
        CryptoCoin("MOG", "Looksmax Coin", "🤫", 50.0, 50.0, 33.3, 0.0),
        CryptoCoin("KOCH", "KochCoin", "💪", 100.0, 100.0, 18.0, 0.0)
    )

    val ALLOWED_LETTERS = listOf("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х")

    val RUSSIAN_REGIONS = listOf(
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "21",
        "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
        "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
        "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
        "52", "53", "54", "55", "56", "57", "58", "59", "60", "61",
        "62", "63", "64", "65", "66", "67", "68", "69", "70", "71",
        "72", "73", "74", "75", "76", "77", "78", "79", "82", "86",
        "89", "92", "97", "99", "102", "116", "123", "125", "134",
        "138", "150", "152", "154", "159", "161", "163", "164", "174",
        "177", "178", "186", "190", "196", "197", "198", "199", "702",
        "716", "750", "763", "777", "790", "797", "799", "333"
    )

    fun spinRandomPlate(): RussianPlate {
        val l1 = ALLOWED_LETTERS.random()
        val l2 = ALLOWED_LETTERS.random()
        val l3 = ALLOWED_LETTERS.random()
        val series = "$l1$l2$l3"
        val seriesRest = "$l2$l3"

        val numInt = Random.nextInt(1, 1000)
        val num = String.format("%03d", numInt)

        val reg = if (Random.nextInt(100) < 85) {
            RUSSIAN_REGIONS.random()
        } else {
            String.format("%02d", Random.nextInt(1, 100))
        }

        val isDenisov333 = num == "333" || reg == "333"
        val isSpecialSeries = series in listOf("ЕКХ", "АМР", "ВОР", "ААА", "ООО", "ХХХ", "ССС", "МММ")
        val isTripleDigits = num[0] == num[1] && num[1] == num[2]
        val isMirror = num[0] == num[2]
        val isDoubleLetters = l2 == l3 || l1 == l2

        val full = "$l1 $num $seriesRest $reg"

        return when {
            isDenisov333 -> {
                val clickB = 1.20 + Random.nextDouble(0.20, 0.80)
                val passB = 1.20 + Random.nextDouble(0.20, 0.80)
                RussianPlate(full, l1, num, seriesRest, reg, PlateRarity.LEGENDARY, "Пенисов 333 Special", clickB, passB)
            }
            isSpecialSeries -> {
                val clickB = 1.00 + Random.nextDouble(0.20, 0.70)
                val passB = 1.00 + Random.nextDouble(0.20, 0.60)
                RussianPlate(full, l1, num, seriesRest, reg, PlateRarity.LEGENDARY, "Спецсерия «$series»", clickB, passB)
            }
            isTripleDigits -> {
                val clickB = 0.70 + Random.nextDouble(0.10, 0.40)
                val passB = 0.65 + Random.nextDouble(0.10, 0.40)
                RussianPlate(full, l1, num, seriesRest, reg, PlateRarity.EPIC, "Три одинаковые цифры $num", clickB, passB)
            }
            isMirror || isDoubleLetters -> {
                val clickB = 0.35 + Random.nextDouble(0.05, 0.25)
                val passB = 0.30 + Random.nextDouble(0.05, 0.25)
                val name = if (isMirror) "Красивый зеркальный номер" else "Парные буквы $series"
                RussianPlate(full, l1, num, seriesRest, reg, PlateRarity.RARE, name, clickB, passB)
            }
            else -> {
                val clickB = 0.10 + Random.nextDouble(0.02, 0.15)
                val passB = 0.08 + Random.nextDouble(0.02, 0.12)
                RussianPlate(full, l1, num, seriesRest, reg, PlateRarity.COMMON, "Городской госномер", clickB, passB)
            }
        }
    }
}
