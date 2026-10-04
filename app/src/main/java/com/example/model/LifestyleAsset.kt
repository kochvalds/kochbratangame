package com.example.model

enum class PlateRarity(val titleRu: String, val colorHex: Long) {
    COMMON("Обычный", 0xFF9E9E9E),
    RARE("Зеркальный", 0xFF00E5FF),
    EPIC("Блатной", 0xFFD500F9),
    LEGENDARY("Правительственный", 0xFFFFD600)
}

data class RussianPlate(
    val fullPlate: String,      // e.g. "Е 333 КХ 777"
    val seriesFirst: String,   // "Е"
    val number: String,        // "333"
    val seriesRest: String,    // "КХ"
    val region: String,        // "777"
    val rarity: PlateRarity,
    val specialName: String,   // e.g. "Серия «Еду Как Хочу»", "Денисов 333 Special"
    val clickBonus: Double,    // +%
    val passiveBonus: Double   // +%
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
            id = "vaz_2107",
            name = "ВАЗ 2107 «Боевая Классика»",
            brand = "LADA",
            description = "Тонировка в круг 5%, заваренный редуктор, выворот рычагов Красноярск. Готова к суете на парковке.",
            iconEmoji = "🚗",
            costAura = 5000.0,
            clickMultiplierBonus = 0.20,
            passiveMultiplierBonus = 0.10,
            speedKmh = 160,
            horsepower = 85
        ),
        CarAsset(
            id = "bmw_m5",
            name = "BMW M5 F90 Competition",
            brand = "BMW M Power",
            description = "Stage 2, даунпайпы, прострелы выхлопа на всю улицу. Разрывает светофоры города.",
            iconEmoji = "🏎️",
            costAura = 60000.0,
            clickMultiplierBonus = 0.50,
            passiveMultiplierBonus = 0.40,
            speedKmh = 305,
            horsepower = 750
        ),
        CarAsset(
            id = "mercedes_g63",
            name = "Mercedes-AMG G63 (Гелик)",
            brand = "Mercedes-Benz",
            description = "Черный матовый кузов, выхлоп в бока, стробоскопы. Полный приоритет в левом ряду трассы.",
            iconEmoji = "🚙",
            costAura = 250000.0,
            clickMultiplierBonus = 0.90,
            passiveMultiplierBonus = 0.75,
            speedKmh = 240,
            horsepower = 585
        ),
        CarAsset(
            id = "porsche_911",
            name = "Porsche 911 GT3 RS",
            brand = "Porsche",
            description = "Огромное карбоновое антикрыло с DRS, керамические тормоза и секунды на Нюрбургринге.",
            iconEmoji = "🏁",
            costAura = 750000.0,
            clickMultiplierBonus = 1.40,
            passiveMultiplierBonus = 1.20,
            speedKmh = 320,
            horsepower = 525
        ),
        CarAsset(
            id = "rolls_royce",
            name = "Rolls-Royce Phantom VIII",
            brand = "Rolls-Royce",
            description = "Потолок «Звездное небо», массажные кресла и открывающиеся против хода двери. Абсолютный статус.",
            iconEmoji = "👑",
            costAura = 2500000.0,
            clickMultiplierBonus = 2.20,
            passiveMultiplierBonus = 2.00,
            speedKmh = 250,
            horsepower = 571
        ),
        CarAsset(
            id = "bugatti_chiron",
            name = "Bugatti Chiron Pur Sport",
            brand = "Bugatti",
            description = "8.0-литровый W16 с 4 турбинами. 1500 сил чистого моггинга пространства и времени.",
            iconEmoji = "⚡",
            costAura = 10000000.0,
            clickMultiplierBonus = 4.50,
            passiveMultiplierBonus = 4.00,
            speedKmh = 420,
            horsepower = 1500
        )
    )

    val REAL_ESTATE = listOf(
        RealEstateAsset(
            id = "khrushchevka",
            name = "Однушка в Хрущёвке",
            location = "Спальный район",
            description = "Ковер на стене, скрипучий паркет и турник в дверном проеме. Уютное логово начинающего сигмы.",
            iconEmoji = "🏚️",
            costAura = 12000.0,
            passiveAuraPerSec = 150.0,
            auraMultiplierBonus = 0.15
        ),
        RealEstateAsset(
            id = "moscow_city",
            name = "Апартаменты в Москва-Сити",
            location = "Пресненская наб., Башня Федерация",
            description = "68 этаж, панорамные окна в пол, вид на всю Москву. Здесь заключаются многомиллионные сделки.",
            iconEmoji = "🏙️",
            costAura = 150000.0,
            passiveAuraPerSec = 2200.0,
            auraMultiplierBonus = 0.50
        ),
        RealEstateAsset(
            id = "patriarshie",
            name = "Пентхаус на Патриарших",
            location = "Патриаршие Пруды",
            description = "Эпицентр роскоши, модных кофеен и топовых луксмаксеров столицы. Личная терраса на крыше.",
            iconEmoji = "🍸",
            costAura = 700000.0,
            passiveAuraPerSec = 12000.0,
            auraMultiplierBonus = 0.90
        ),
        RealEstateAsset(
            id = "dubai_villa",
            name = "Вилла на Palm Jumeirah",
            location = "Дубай, ОАЭ",
            description = "Собственный песчаный пляж у Персидского залива, вертолетная площадка и золотой гараж для спорткаров.",
            iconEmoji = "🌴",
            costAura = 3500000.0,
            passiveAuraPerSec = 65000.0,
            auraMultiplierBonus = 1.80
        ),
        RealEstateAsset(
            id = "swiss_castle",
            name = "Замок в Швейцарских Альпах",
            location = "Санкт-Мориц, Швейцария",
            description = "Абсолютная цитадель Апекс Бога. Горные вершины, термальные спа-источники и тишина превосходства.",
            iconEmoji = "🏰",
            costAura = 20000000.0,
            passiveAuraPerSec = 400000.0,
            auraMultiplierBonus = 3.50
        )
    )

    val INITIAL_CRYPTO = listOf(
        CryptoCoin("BTC", "Bitcoin", "🪙", 65000.0, 65000.0, 4.2, 0.0),
        CryptoCoin("ETH", "Ethereum", "🔷", 3500.0, 3500.0, -1.8, 0.0),
        CryptoCoin("TON", "Toncoin", "💎", 700.0, 700.0, 12.5, 0.0),
        CryptoCoin("SOL", "Solana", "🟣", 1600.0, 1600.0, 8.4, 0.0),
        CryptoCoin("MOG", "Looksmax Coin", "🤫", 50.0, 50.0, 33.3, 0.0),
        CryptoCoin("KOCH", "KochCoin", "💪", 100.0, 100.0, 18.0, 0.0)
    )

    // Letters allowed on Russian plates: А, В, Е, К, М, Н, О, Р, С, Т, У, Х
    private val ALLOWED_LETTERS = listOf("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х")
    private val POPULAR_REGIONS = listOf("777", "77", "99", "199", "97", "52", "152", "333")

    fun spinRandomPlate(): RussianPlate {
        val roll = kotlin.random.Random.nextInt(100)

        return when {
            // Denisov 333 Special Legendary / Government (5% chance)
            roll < 5 -> {
                val specialType = kotlin.random.Random.nextInt(4)
                when (specialType) {
                    0 -> RussianPlate("Е 333 КХ 777", "Е", "333", "КХ", "777", PlateRarity.LEGENDARY, "«Еду Как Хочу» Пенисов 333 Special", 1.50, 1.50)
                    1 -> RussianPlate("А 333 МР 97", "А", "333", "МР", "97", PlateRarity.LEGENDARY, "Правительственный Пенисов 333", 1.40, 1.40)
                    2 -> RussianPlate("В 333 ОР 777", "В", "333", "ОР", "777", PlateRarity.LEGENDARY, "Блатной ВОР 333", 1.60, 1.20)
                    else -> RussianPlate("Х 333 ХХ 333", "Х", "333", "ХХ", "333", PlateRarity.LEGENDARY, "Чистые 333 на 333 регионе", 1.70, 1.70)
                }
            }
            // Legendary series ЕКХ / АМР / ВОР (12% chance)
            roll < 17 -> {
                val series = listOf("ЕКХ", "АМР", "ВОР", "ААА", "ООО", "ХХХ").random()
                val num = listOf("777", "001", "007", "999", "555").random()
                val reg = listOf("777", "97", "77", "99").random()
                val (f, rest) = series.first().toString() to series.substring(1)
                RussianPlate("$f $num $rest $reg", f, num, rest, reg, PlateRarity.LEGENDARY, "Спецсерия «$series»", 1.00, 1.00)
            }
            // Epic identical digits or mirror numbers (28% chance)
            roll < 45 -> {
                val letter1 = ALLOWED_LETTERS.random()
                val letter2 = ALLOWED_LETTERS.random()
                val letter3 = ALLOWED_LETTERS.random()
                val num = listOf("111", "222", "444", "555", "666", "777", "888", "999", "101", "707", "007").random()
                val reg = POPULAR_REGIONS.random()
                val rest = "$letter2$letter3"
                val name = if (num[0] == num[2]) "Красивый зеркальный номер" else "Три одинаковые цифры"
                RussianPlate("$letter1 $num $rest $reg", letter1, num, rest, reg, PlateRarity.EPIC, name, 0.50, 0.50)
            }
            // Rare matching letters (25% chance)
            roll < 70 -> {
                val letter = ALLOWED_LETTERS.random()
                val num = kotlin.random.Random.nextInt(10, 99).toString().padStart(3, '0')
                val reg = POPULAR_REGIONS.random()
                RussianPlate("$letter $num $letter$letter $reg", letter, num, "$letter$letter", reg, PlateRarity.RARE, "Одинаковые буквы $letter$letter$letter", 0.25, 0.25)
            }
            // Common street plate
            else -> {
                val l1 = ALLOWED_LETTERS.random()
                val l2 = ALLOWED_LETTERS.random()
                val l3 = ALLOWED_LETTERS.random()
                val num = kotlin.random.Random.nextInt(100, 999).toString()
                val reg = POPULAR_REGIONS.random()
                val rest = "$l2$l3"
                RussianPlate("$l1 $num $rest $reg", l1, num, rest, reg, PlateRarity.COMMON, "Городской госномер", 0.10, 0.05)
            }
        }
    }
}
