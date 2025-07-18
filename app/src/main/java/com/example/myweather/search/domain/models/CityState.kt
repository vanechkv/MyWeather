package com.example.myweather.search.domain.models

sealed interface CityState {
    object Loading : CityState

    data class Content(
        val city: List<City>
    ) : CityState

    data class Error(
        val message: String
    ) : CityState

    data class Empty(
        val message: Int
    ) : CityState
}