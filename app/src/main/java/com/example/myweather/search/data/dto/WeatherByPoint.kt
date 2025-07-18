package com.example.myweather.search.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherByPoint(
    @SerializedName("now") val now: WeatherDto
)
