package com.example.myweather.search.data.response

import com.google.gson.annotations.SerializedName

class CitySearchResponse(
    @SerializedName("response") val response: GeoResponse,
)