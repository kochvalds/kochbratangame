package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stats")
data class GameStats(
    @PrimaryKey val id: Int = 1,
    val auraPoints: Double = 0.0,
    val totalAuraEarned: Double = 0.0,
    val totalMogs: Long = 0L,
    val bonesmashHits: Long = 0L,
    val evolutionStage: Int = 0, // 0 = Subfive, 6 = Apex Mogger God
    val jawLevel1: Int = 1, // Mewing
    val jawLevel2: Int = 0, // Hammer bonesmash
    val jawLevel3: Int = 0, // Mastic gum
    val jawLevel4: Int = 0, // Square chin
    val jawLevel5: Int = 0, // 90 deg gonial
    val jawLevel6: Int = 0, // Hollow cheeks
    val jawLevel7: Int = 0, // Diamond jaw
    val jawLevel8: Int = 0, // Turbo Pisyun Mogging 333
    val passiveLevel1: Int = 0, // Ice bath & Gua Sha
    val passiveLevel2: Int = 0, // SPF 100 & Retinol
    val passiveLevel3: Int = 0, // Hunter Eyes
    val passiveLevel4: Int = 0, // Positive Canthal Tilt
    val passiveLevel5: Int = 0, // Nocturnal Mewing
    val passiveLevel6: Int = 0, // Sigma Walk
    val passiveLevel7: Int = 0, // Mogger Squad
    val kochLevel1: Int = 0, // Turnik
    val kochLevel2: Int = 0, // Creatine
    val kochLevel3: Int = 0, // Bench 140kg
    val kochLevel4: Int = 0, // Koch Spirit
    val kochAdrenaline: Float = 0f,
    val lastTimestamp: Long = System.currentTimeMillis(),
    val hapticsEnabled: Boolean = true,
    val selectedSkinId: String = "vlados_default",
    val unlockedSkinIds: String = "vlados_default,kochvalds_default,voidoc_classic"
)
