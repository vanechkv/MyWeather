package com.example.myweather.search.data.dto

import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    val featureMember: List<FeatureMember>
)
