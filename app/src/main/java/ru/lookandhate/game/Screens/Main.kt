package ru.lookandhate.game.Screens

import android.util.Log
import androidx.compose.runtime.Composable
import ru.lookandhate.game.Game
import ru.lookandhate.game.Snake

@Composable
fun Main(game: Game) {
    Log.d("Main", "Recomposition.")
        Snake(game = game)

}