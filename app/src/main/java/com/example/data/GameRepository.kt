package com.example.data

import com.example.model.GameStats
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {
    val statsFlow: Flow<GameStats?> = gameDao.getGameStats()

    suspend fun getStatsDirect(): GameStats {
        return gameDao.getGameStatsDirect() ?: GameStats()
    }

    suspend fun saveStats(stats: GameStats) {
        gameDao.insertGameStats(stats.copy(lastTimestamp = System.currentTimeMillis()))
    }
}
