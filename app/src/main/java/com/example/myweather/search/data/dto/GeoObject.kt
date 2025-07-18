package com.example.myweather.search.data.dto

import com.google.gson.annotations.SerializedName

data class GeoObject(
    val uri: String?,
    val name: String?,
    val description: String?,
    @SerializedName("Point") val point: Point?
)
