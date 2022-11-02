package ru.lookandhate.game.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameResult::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao

}