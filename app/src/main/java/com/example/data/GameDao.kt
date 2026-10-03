package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.GameStats
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_stats WHERE id = 1 LIMIT 1")
    fun getGameStats(): Flow<GameStats?>

    @Query("SELECT * FROM game_stats WHERE id = 1 LIMIT 1")
    suspend fun getGameStatsDirect(): GameStats?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameStats(stats: GameStats)

    @Update
    suspend fun updateGameStats(stats: GameStats)
}
