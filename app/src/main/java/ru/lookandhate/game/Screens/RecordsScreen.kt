package ru.lookandhate.game.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.lookandhate.game.MainActivity
import ru.lookandhate.game.Room.GameResult
import ru.lookandhate.game.Room.GameResultDao

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun RecordScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current as MainActivity
    var records: List<GameResult> = listOf()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            records = context.getDB()!!.gameResultDao().getAll()
        }
    }
    records.forEach {
        RecordRow(record = it)
    }
}

@Composable
fun RecordRow(record: GameResult) {
    Row() {
        Column {
            Text(text = record.date.toString())
        }
        Column {
            Text(text = record.points.toString())
        }
    }

}