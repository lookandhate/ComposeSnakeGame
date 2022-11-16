package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.lookandhate.game.Room.GameResult
import java.util.*

@Composable
fun RecordScreen(records: List<GameResult>) {
    val recordsState = remember { mutableStateListOf<GameResult>() }
    records.forEach { recordsState.add(it) }
    Log.d("Records", records.toString())
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item() {
            Row(
            ) {
                Column(modifier = Modifier.weight(0.7f)) {
                    ClickableText(
                        text = AnnotatedString("Game time"),
                        style= TextStyle(fontSize = 20.sp, color = Color.Blue),
                        onClick = {
                            recordsState.sortByDescending { it.date }
                        })
                }
                Column(modifier = Modifier.weight(0.3f)) {
                    ClickableText(text=AnnotatedString("Points"),
                        style= TextStyle(fontSize = 20.sp, color = Color.Blue),
                        onClick = {
                        recordsState.sortByDescending { it.points }

                    })
                }

            }
            Divider()
        }

        items(recordsState) { record ->
            RecordItem(record)

        }
    }
}

@Composable
fun RecordItem(record: GameResult) {
    Row(
    ) {
        Column(modifier = Modifier.weight(0.7f)) {
            Text(text = "${(record.date_start - record.date) / -1000} seconds")
        }
        Column(Modifier.weight(0.3f)) {
            Text(text = record.points.toString())
        }

    }
}