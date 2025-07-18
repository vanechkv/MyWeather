package com.example.myweather.search.data.network

import com.example.myweather.search.data.response.CitySearchResponse
import com.example.myweather.search.data.response.Result
import com.example.myweather.search.data.response.WeatherResponse

interface NetworkClient {
    suspend fun searchCity(dto: Any): Result<CitySearchResponse>
    suspend fun getNowWeatherByPoint(dto: Any): Result<WeatherResponse>
}