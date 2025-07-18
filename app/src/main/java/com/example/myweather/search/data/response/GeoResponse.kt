package com.example.myweather.search.data.response

import com.example.myweather.search.data.dto.GeoObjectCollection
import com.google.gson.annotations.SerializedName

data class GeoResponse(
    @SerializedName("GeoObjectCollection")
    val geoObjectCollection: GeoObjectCollection
)