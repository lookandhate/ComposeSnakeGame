package ru.lookandhate.game.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameResultDao {
    @Query("SELECT * FROM GameResult ORDER BY result DESC")
    suspend fun getAll(): List<GameResult>

    @Insert()
    suspend fun insert(gameResult: GameResult)

}