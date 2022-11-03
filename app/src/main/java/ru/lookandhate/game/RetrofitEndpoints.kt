package ru.lookandhate.game

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import ru.lookandhate.game.Room.GameResult

interface RetrofitEndpoints {
    @GET("records")
    suspend fun getRecords(): Response<List<GameResult>>

}