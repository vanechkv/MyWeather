package com.example.myweather.search.data.dto

import com.google.gson.annotations.SerializedName

data class FeatureMember(
    @SerializedName("GeoObject")
    val geoObject: GeoObject
)
