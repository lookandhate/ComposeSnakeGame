package ru.lookandhate.game.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.lookandhate.game.Activities.ui.theme.GameTheme
import ru.lookandhate.game.Room.AppDataBase
import ru.lookandhate.game.Room.GameResult
import ru.lookandhate.game.Screens.RecordScreen

class RecordScreenRest : ComponentActivity() {
    private var db: AppDataBase? = null
    private var records: List<GameResult> = listOf()

    fun getDB(): AppDataBase? {
        return db
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.db = Room.databaseBuilder(this, AppDataBase::class.java, "database")
            .build()
        GlobalScope.launch {
            records = db!!.gameResultDao().getAll()
        }
        setContent {
            GameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecordScreen(this.records)
                }
            }
        }
    }
}
