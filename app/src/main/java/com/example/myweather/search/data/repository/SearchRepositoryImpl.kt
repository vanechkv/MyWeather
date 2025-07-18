package com.example.myweather.search.data.repository

import com.example.myweather.search.data.network.NetworkClient
import com.example.myweather.search.data.request.CitySearchRequest
import com.example.myweather.search.data.response.CitySearchResponse
import com.example.myweather.search.data.response.Result
import com.example.myweather.search.domain.models.City
import com.example.myweather.search.domain.models.Resource
import com.example.myweather.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {
    override fun searchCity(cityName: String): Flow<Resource<List<City>>> = flow {
        when (val response = networkClient.doRequest(CitySearchRequest(cityName))) {
            is Result.NoConnection -> emit(Resource.Error("No internet connection"))

            is Result.Success -> {
                val cities = response.data.response
                    .geoObjectCollection
                    .featureMember
                    .mapNotNull { member ->
                        val geo = member.geoObject
                        val uri = geo.uri ?: return@mapNotNull null
                        val name = geo.name ?: return@mapNotNull null
                        val description = geo.description ?: return@mapNotNull null
                        val point = geo.point?.pos ?: return@mapNotNull null
                        City(uri = uri, name = name, description = description, point = point)
                    }
                emit(Resource.Success(cities))
            }

            is Result.Error -> emit(Resource.Error(response.errorResponse.message))
            is Result.BadRequest -> emit(Resource.Error(response.code.toString()))
        }
    }
}