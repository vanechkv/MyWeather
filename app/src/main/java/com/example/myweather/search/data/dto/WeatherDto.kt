package com.example.myweather.search.data.dto

data class WeatherDto(
    val cloudiness: String?,
    val humidity: Int?,
    val precType: String?,
    val precStrength: String?,
    val pressure: Int?,
    val temperature: Int?,
    val fahrenheit: Int?,
    val windSpeed: Int?,
    val windDirection: String?
)