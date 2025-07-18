package com.example.myweather.search.domain.repository

import com.example.myweather.search.domain.models.City
import com.example.myweather.search.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchCity(cityName: String): Flow<Resource<List<City>>>
}