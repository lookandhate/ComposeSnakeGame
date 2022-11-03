package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.lookandhate.game.Room.GameResult
import java.util.*

@Composable
fun RecordScreen(records: List<GameResult>) {
//    Text(text = "Records")
    Log.d("Records", records.toString())
    Column() {


        records.forEach {
            RecordRow(record = it)
        }
    }
}

@Composable
fun RecordRow(record: GameResult) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Column() {
            Text(text = Date(record.date).toString())
        }

        Column {
            Text(text = record.points.toString())
        }
    }

}