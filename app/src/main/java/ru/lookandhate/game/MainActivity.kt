package ru.lookandhate.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.lookandhate.game.Room.AppDataBase
import ru.lookandhate.game.Room.GameResult
import ru.lookandhate.game.Room.GameResultDao
import ru.lookandhate.game.ui.theme.GameTheme
import kotlin.random.Random

data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    var points: Int,
    var snakeLength: Int = 4
)

class Game(val scope: CoroutineScope, context: MainActivity) {
    private val mutableState: MutableStateFlow<State> =
        MutableStateFlow(State(Pair(8, 8), listOf(Pair(0, 0)), 0))
    val state: Flow<State> = mutableState
    private val mutex = Mutex()
    private val db = Room.databaseBuilder(context, AppDataBase::class.java, "database")
        .build()

    var move = Pair(0, 1)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    private suspend fun loseGame(gameState: State) {
        val gameResultDao = db.gameResultDao()

        gameResultDao.insert(
            GameResult(
                points = gameState.points,
                date = System.currentTimeMillis()
            )
        )
        gameState.snakeLength = 4
        gameState.points = 0
    }

    private fun eatFood(gameState: State) {
        gameState.snakeLength++
        gameState.points++
    }

    private suspend fun getNewPosition(gameState: State): Pair<Int, Int> {
        val newPosition = gameState.snake.first().let { pos ->
            mutex.withLock {
                Pair(
                    (pos.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                    (pos.second + move.second + BOARD_SIZE) % BOARD_SIZE
                )
            }
        }
        return newPosition
    }


    companion object {
        const val BOARD_SIZE = 16
    }


    init {
        scope.launch {
            while (true) {
                delay(150)
                mutableState.update {
                    val newPosition = getNewPosition(it)
                    if (newPosition == it.food) {
                        eatFood(it)
                    }

                    if (it.snake.contains(newPosition)) {
                        loseGame(it)
                    }

                    it.copy(
                        food = if (newPosition == it.food) {
                            Pair(
                                (Random.nextInt(BOARD_SIZE)),
                                (Random.nextInt(BOARD_SIZE))
                            )
                        } else {
                            it.food
                        },
                        snake = listOf(newPosition) + it.snake.take(it.snakeLength - 1)
                    )
                }
            }
        }
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val game = Game(lifecycleScope, this)
        setContent {
            GameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Snake(game = game)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameTheme {
        Greeting("Android")
    }
}

@Composable
fun Snake(game: Game) {
    val state = game.state.collectAsState(initial = null)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Points: ${state.value?.points}")
        state.value?.let {
            Board(it)
        }
        Buttons({
            game.move = it
        })


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
        val titleSize = maxWidth / Game.BOARD_SIZE

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color.Green)
        )
        Box(
            Modifier
                .offset(x = titleSize * state.food.first, y = titleSize * state.food.second)
                .size(titleSize)
                .background(Color.Red, CircleShape)

        )

        state.snake.forEach {
            Box(
                Modifier
                    .offset(x = titleSize * it.first, y = titleSize * it.second)
                    .size(titleSize)
                    .background(Color.Green)
                    .border(2.dp, Color.DarkGray)
            )
        }


    }
}