package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import ru.lookandhate.game.Room.GameResult
import java.util.*

@Composable
fun RecordScreen(records: List<GameResult>) {
    val recordsState = remember { mutableStateListOf<GameResult>() }
    records.forEach { recordsState.add(it) }
    Log.d("Records", records.toString())
    LazyColumn {
        item() {
            Row(
            ) {
                Column(modifier = Modifier.weight(0.7f)) {
                    ClickableText(AnnotatedString("Date of record"), onClick = {
                        recordsState.sortByDescending { it.date }
                    })
                }
                Column( Modifier.weight(0.3f)) {
                    ClickableText(AnnotatedString("Points"), onClick = {
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
            Text(text = Date(record.date).toString())
        }
        Column( Modifier.weight(0.3f)) {
            Text(text = record.points.toString())
        }

    }
}