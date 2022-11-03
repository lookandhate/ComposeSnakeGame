package ru.lookandhate.game

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {
    private val apiURL = "http://192.168.0.104:8080"
    val retrofit = Retrofit.Builder()
        .baseUrl(apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitEndpoints::class.java)
}