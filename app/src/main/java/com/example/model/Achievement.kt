package com.example.model

data class AchievementDef(
    val id: String,
    val title: String,
    val description: String,
    val rewardAura: Double,
    val iconEmoji: String
)

data class AchievementItem(
    val def: AchievementDef,
    val isUnlocked: Boolean,
    val isClaimed: Boolean
)

object AchievementsCatalog {
    val ACHIEVEMENTS = listOf(
        AchievementDef(
            id = "first_mog",
            title = "Первый Мог",
            description = "Нанеси свой первый сокрушительный клик по фоидке",
            rewardAura = 100.0,
            iconEmoji = "🤫"
        ),
        AchievementDef(
            id = "combo_king",
            title = "Серия Моггинга",
            description = "Набери комбо x10 быстрых кликов подряд",
            rewardAura = 500.0,
            iconEmoji = "🔥"
        ),
        AchievementDef(
            id = "bonesmash_25",
            title = "Микротрещины в кости",
            description = "Нанеси 25 ударов бонсмешинга молотком",
            rewardAura = 1500.0,
            iconEmoji = "🔨"
        ),
        AchievementDef(
            id = "bonesmash_100",
            title = "Алмазная Нижняя Треть",
            description = "Нанеси 100 ударов бонсмешинга по челюсти",
            rewardAura = 10000.0,
            iconEmoji = "🗿"
        ),
        AchievementDef(
            id = "koch_mode",
            title = "Ярость Кочалки",
            description = "Заряди адреналин на 100% и активируй режим «Кочнуть Братана»",
            rewardAura = 5000.0,
            iconEmoji = "💪"
        ),
        AchievementDef(
            id = "boris_unlocked",
            title = "Поясни за шмот",
            description = "Разблокируй или выбери скин Бориса Офника",
            rewardAura = 20000.0,
            iconEmoji = "🧢"
        ),
        AchievementDef(
            id = "vaska_unlocked",
            title = "Идеальный Баззкат",
            description = "Разблокируй или выбери скин Васьки Стригуна",
            rewardAura = 25000.0,
            iconEmoji = "✂️"
        ),
        AchievementDef(
            id = "skin_collector",
            title = "Гардеробная Сигмы",
            description = "Разблокируй 5 или более скинов персонажей",
            rewardAura = 50000.0,
            iconEmoji = "👔"
        ),
        AchievementDef(
            id = "stage_mewing",
            title = "Язык к нёбу",
            description = "Эволюционируй в Адепта Мьюинга (Stage 3)",
            rewardAura = 25000.0,
            iconEmoji = "🧏"
        ),
        AchievementDef(
            id = "stage_chad",
            title = "Взгляд Охотника",
            description = "Эволюционируй в Перспективного Чадлайта (Stage 4)",
            rewardAura = 100000.0,
            iconEmoji = "🦅"
        ),
        AchievementDef(
            id = "stage_gigachad",
            title = "Истинный Гигачад",
            description = "Достигни 6-й стадии эволюции лица",
            rewardAura = 1000000.0,
            iconEmoji = "👑"
        ),
        AchievementDef(
            id = "passive_mogger",
            title = "Авто-Моггер",
            description = "Развей привлекательность до +500 ауры в секунду",
            rewardAura = 250000.0,
            iconEmoji = "✨"
        ),
        AchievementDef(
            id = "aura_millionaire",
            title = "Аура-Магнат",
            description = "Заработай суммарно более 1 000 000 очков ауры",
            rewardAura = 500000.0,
            iconEmoji = "💎"
        )
    )
}
