package ru.lookandhate.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Snake(game: Game) {
    val state = game.state.collectAsState(initial = null)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Points: ${state.value?.points}")
        state.value?.let {
            Board(it)
        }
        Buttons {
            game.move = it
        }


    }

}

@Composable
fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
    val buttonsSize = Modifier.size(64.dp)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { onDirectionChange(Pair(0, -1)) }, modifier = buttonsSize) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
        }

        Row() {

            Button(onClick = { onDirectionChange(Pair(-1, 0)) }, modifier = buttonsSize) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }

            Spacer(modifier = buttonsSize)

            Button(onClick = { onDirectionChange(Pair(1, 0)) }, modifier = buttonsSize) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }

        Button(onClick = { onDirectionChange(Pair(0, 1)) }, modifier = buttonsSize) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    }
}

@Composable
fun Board(state: State) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val snakePartSize = maxWidth / Game.BOARD_SIZE

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color.Green)
        )
        Box(
            Modifier
                .offset(x = snakePartSize * state.food.first, y = snakePartSize * state.food.second)
                .size(snakePartSize)
                .background(Color.Red, CircleShape)

        )

        state.snake.forEach {
            Box(
                Modifier
                    .offset(x = snakePartSize * it.first, y = snakePartSize * it.second)
                    .size(snakePartSize)
                    .background(Color.Green)
                    .border(2.dp, Color.DarkGray)
            )
        }


    }
}