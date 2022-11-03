package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lookandhate.game.Activities.RecordsScreen
import ru.lookandhate.game.Room.GameResult

@OptIn(DelicateCoroutinesApi::class)
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
            Text(text = record.date.toString())
        }

        Column {
            Text(text = record.points.toString())
        }
    }

}