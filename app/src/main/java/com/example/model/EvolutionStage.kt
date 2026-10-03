package com.example.model

data class EvolutionStageInfo(
    val stage: Int,
    val nameRu: String,
    val subtitleRu: String,
    val pslScore: Double,
    val reqAura: Double,
    val clickMultiplier: Double,
    val passiveMultiplier: Double,
    val description: String,
    val memeQuote: String,
    val profileFeature: String
)

object EvolutionStages {
    val STAGES = listOf(
        EvolutionStageInfo(
            stage = 0,
            nameRu = "Подзаборный Сойджак",
            subtitleRu = "Level 1: Subfive (PSL 1.5)",
            pslScore = 1.5,
            reqAura = 0.0,
            clickMultiplier = 1.0,
            passiveMultiplier = 1.0,
            description = "Рот открыт, шея согнута вперед от телефона. Фоидки смеются при встрече.",
            memeQuote = "«Брат, зачем мьюить, генетика решает...»",
            profileFeature = "Скошенный подбородок, нулевая челюсть"
        ),
        EvolutionStageInfo(
            stage = 1,
            nameRu = "Случайный Нормис",
            subtitleRu = "Level 2: Normie (PSL 3.2)",
            pslScore = 3.2,
            reqAura = 250.0,
            clickMultiplier = 2.0,
            passiveMultiplier = 1.5,
            description = "Начал выпрямлять спину и закрыл рот. Заметил первые изменения в зеркале.",
            memeQuote = "«Я просто умылся холодной водой!»",
            profileFeature = "Прямая осанка, базовая линия нижней челюсти"
        ),
        EvolutionStageInfo(
            stage = 2,
            nameRu = "Адепт Мьюинга",
            subtitleRu = "Level 3: Mewing Adept (PSL 4.8)",
            pslScore = 4.8,
            reqAura = 2500.0,
            clickMultiplier = 4.0,
            passiveMultiplier = 2.5,
            description = "Язык плотно прижат к нёбу 24/7. Появились первые тени и впадины на щеках.",
            memeQuote = "«🤫🧏 Брат, не говори со мной, я на стрике мьюинга.»",
            profileFeature = "Поднятая подъязычная кость, контур скул"
        ),
        EvolutionStageInfo(
            stage = 3,
            nameRu = "Перспективный Чадлайт",
            subtitleRu = "Level 4: Chadlite (PSL 6.2)",
            pslScore = 6.2,
            reqAura = 25000.0,
            clickMultiplier = 8.0,
            passiveMultiplier = 5.0,
            description = "Взгляд охотника (Hunter eyes) сформирован. Фоидки нервно отводят взгляд.",
            memeQuote = "«Мой гониальный угол режет взгляды завистников.»",
            profileFeature = "Положительный кантус, острые скулы"
        ),
        EvolutionStageInfo(
            stage = 4,
            nameRu = "Коч Братан (Koch Mogger)",
            subtitleRu = "Level 5: Koch Mogger (PSL 7.5)",
            pslScore = 7.5,
            reqAura = 150000.0,
            clickMultiplier = 16.0,
            passiveMultiplier = 10.0,
            description = "Кочалка каждый день. Шея как ствол векового дуба, квадратная стальная челюсть.",
            memeQuote = "«Кочнули челюсть, кочнули спину. Братаны вперед!»",
            profileFeature = "Трапеции монстра, массивная нижняя челюсть"
        ),
        EvolutionStageInfo(
            stage = 5,
            nameRu = "Истинный Гигачад",
            subtitleRu = "Level 6: True GigaChad (PSL 8.5)",
            pslScore = 8.5,
            reqAura = 1000000.0,
            clickMultiplier = 35.0,
            passiveMultiplier = 25.0,
            description = "Идеальный 90° гониальный угол, глубокие полые щеки. Могает аудиторию взглядом.",
            memeQuote = "«Can you feel my heart? Да, я могаю фоидок.»",
            profileFeature = "Идеальная симметрия лица, бритвенная резкость"
        ),
        EvolutionStageInfo(
            stage = 6,
            nameRu = "Апекс Бог Луксмакса",
            subtitleRu = "Level 7: Apex Hunter God (PSL 9.9)",
            pslScore = 9.9,
            reqAura = 10000000.0,
            clickMultiplier = 100.0,
            passiveMultiplier = 75.0,
            description = "Трансцендентная аура. Пространство деформируется вокруг остроты челюсти.",
            memeQuote = "«Bye bye 🤫🧏 Фоидки стерты в порошок силой ауры.»",
            profileFeature = "Божественный профиль, абсолютный угол челюсти"
        )
    )

    fun getStage(stageIndex: Int): EvolutionStageInfo {
        return STAGES.getOrElse(stageIndex.coerceIn(0, STAGES.lastIndex)) { STAGES[0] }
    }
}
