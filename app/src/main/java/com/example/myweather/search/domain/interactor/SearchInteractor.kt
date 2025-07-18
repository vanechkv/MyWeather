package com.example.myweather.search.domain.interactor

import com.example.myweather.search.domain.models.City
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun searchCity(cityName: String): Flow<Pair<List<City>?, String?>>
}