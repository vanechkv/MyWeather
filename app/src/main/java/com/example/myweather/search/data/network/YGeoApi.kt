package com.example.myweather.search.data.network

import com.example.myweather.search.data.response.CitySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YGeoApi {

    @GET("/v1")
    suspend fun searchCity(@Query("geocode") cityName: String): Response<CitySearchResponse>
}