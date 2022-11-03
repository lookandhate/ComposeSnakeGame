package ru.lookandhate.game

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.lookandhate.game.Room.GameResult
import ru.lookandhate.game.Room.GameResultWithoutID

interface RetrofitEndpoints {
    @GET("records")
    suspend fun getRecords(): Response<List<GameResult>>
    @POST("record")
    suspend fun postRecord(@Body body: GameResultWithoutID): Response<GameResult>
}