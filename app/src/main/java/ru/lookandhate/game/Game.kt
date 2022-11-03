package ru.lookandhate.game

import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.lookandhate.game.Activities.MainActivity
import ru.lookandhate.game.Activities.RecordsScreen
import ru.lookandhate.game.Room.GameResult
import kotlin.random.Random

data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    var points: Int,
    var snakeLength: Int = 4,
)

class Game(val scope: CoroutineScope, private val context: MainActivity) {
    private val mutableState: MutableStateFlow<State> =
        MutableStateFlow(State(Pair(8, 8), listOf(Pair(0, 0)), 0))
    val state: Flow<State> = mutableState
    private val mutex = Mutex()


    val db = context.getDB()

    var move = Pair(0, 1)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    private suspend fun loseGame(gameState: State) {
        Log.d("DB", "$db")
        val gameResultDao = db!!.gameResultDao()

        gameResultDao.insert(
            GameResult(
                points = gameState.points,
                date = System.currentTimeMillis()
            )
        )
        gameState.snakeLength = 4
        gameState.points = 0
        val intent = Intent(this.context, RecordsScreen::class.java)
        context.startActivity(intent)
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