package com.example.myweather.search.data.response

import com.example.myweather.search.data.dto.WeatherByPoint
import com.google.gson.annotations.SerializedName

class WeatherResponse (
    @SerializedName("data") val weatherByPoint: WeatherByPoint
)