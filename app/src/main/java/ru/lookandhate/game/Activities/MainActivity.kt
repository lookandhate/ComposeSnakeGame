package ru.lookandhate.game.Activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import ru.lookandhate.game.Game
import ru.lookandhate.game.Room.AppDataBase
import ru.lookandhate.game.Screens.Main
import ru.lookandhate.game.ui.theme.GameTheme


class MainActivity : ComponentActivity() {
    private var db: AppDataBase? = null

    fun getDB(): AppDataBase? {
        return db
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.db = Room.databaseBuilder(this, AppDataBase::class.java, "database")
            .build()
        val game = Game(lifecycleScope, this)

        Log.d("DB", "DB created $db")
        setContent {
            GameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main(game = game)
                }
            }
        }
    }
}

