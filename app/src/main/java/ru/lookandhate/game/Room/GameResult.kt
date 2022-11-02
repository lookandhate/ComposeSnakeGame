package ru.lookandhate.game.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "result") val points: Int,
    @ColumnInfo(name = "date") val date: Long

)