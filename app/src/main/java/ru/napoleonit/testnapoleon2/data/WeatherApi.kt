package ru.napoleonit.testnapoleon2.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.napoleonit.testnapoleon2.entity.WeatherResponse


interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("appid") appId: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): WeatherResponse
}