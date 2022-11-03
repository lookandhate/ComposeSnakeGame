package ru.lookandhate.game

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import ru.lookandhate.game.Room.GameResult

interface RetrofitEndpoints {
    @GET("records")
    suspend fun getRecords(): Response<List<GameResult>>
    @POST("record")
    suspend fun postRecord(): Response<GameResult>
}