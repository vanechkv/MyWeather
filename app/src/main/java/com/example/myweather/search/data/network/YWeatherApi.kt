package com.example.myweather.search.data.network

import com.example.myweather.search.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YWeatherApi {

    @GET("/v2/now")
    suspend fun getNowWeatherByPoint(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<WeatherResponse>
}