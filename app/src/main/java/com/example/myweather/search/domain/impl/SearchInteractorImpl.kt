package com.example.myweather.search.domain.impl

import com.example.myweather.search.domain.interactor.SearchInteractor
import com.example.myweather.search.domain.models.City
import com.example.myweather.search.domain.models.Resource
import com.example.myweather.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInteractorImpl(
    private val repository: SearchRepository
) : SearchInteractor {
    override fun searchCity(cityName: String): Flow<Pair<List<City>?, String?>> {
        return repository.searchCity(cityName).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}