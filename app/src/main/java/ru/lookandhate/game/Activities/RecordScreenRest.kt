package ru.lookandhate.game.Activities

import android.os.Bundle
import android.util.Log
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
import com.google.gson.GsonBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.lookandhate.game.Activities.ui.theme.GameTheme
import ru.lookandhate.game.RetrofitEndpoints
import ru.lookandhate.game.Room.AppDataBase
import ru.lookandhate.game.Room.GameResult
import ru.lookandhate.game.Screens.RecordScreen

class RecordScreenRest : ComponentActivity() {
    private val apiURL = "http://192.168.0.104:8080"

    private val retrofit = Retrofit.Builder()
        .baseUrl(apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitEndpoints::class.java)

    private var records: List<GameResult> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            records = retrofit.getRecords().body()!!
            Log.d("RETROFIT", "Got response from server. Records: $records")
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
