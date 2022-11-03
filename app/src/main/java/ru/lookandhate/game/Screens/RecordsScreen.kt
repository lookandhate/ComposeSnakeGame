package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.lookandhate.game.Room.GameResult
import java.util.*

@Composable
fun RecordScreen(records: List<GameResult>) {
//    Text(text = "Records")
    Log.d("Records", records.toString())
    LazyColumn {
        item() {
            Row(
            ) {
                Column(modifier = Modifier.weight(0.7f)) {
                    Text(text = "Date of record")
                }
                Column( Modifier.weight(0.3f)) {
                    Text(text = "Points")
                }

            }
            Divider()
        }

        items(records) { record ->
            RecordItem(record)

        }
    }
}

@Composable
fun RecordItem(record: GameResult) {
    Row(
    ) {
        Column(modifier = Modifier.weight(0.7f)) {
            Text(text = Date(record.date).toString())
        }
        Column( Modifier.weight(0.3f)) {
            Text(text = record.points.toString())
        }

    }
}