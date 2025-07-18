package com.example.myweather.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.myweather.search.data.request.CitySearchRequest
import com.example.myweather.search.data.request.WeatherRequest
import com.example.myweather.search.data.response.CitySearchResponse
import com.example.myweather.search.data.response.ErrorResponse
import com.example.myweather.search.data.response.Result
import com.example.myweather.search.data.response.WeatherResponse
import com.google.gson.Gson
import com.google.gson.Strictness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.stream.JsonReader
import java.io.StringReader

class RetrofitNetworkClient(
    private val context: Context,
    private val yGeoApi: YGeoApi,
    private val yWeatherApi: YWeatherApi
) : NetworkClient {
    override suspend fun searchCity(dto: Any): Result<CitySearchResponse> {
        if (!isConnected()) {
            return Result.NoConnection
        }
        if (dto !is CitySearchRequest) {
            return Result.BadRequest(400)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = yGeoApi.searchCity(dto.cityName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.Success(it)
                    } ?: Result.Error(
                        ErrorResponse(
                            statusCode = response.code(),
                            error = "Empty body",
                            message = "Response body is null"
                        )
                    )
                } else {
                    val errorBodyStr = response.errorBody()?.string()
                    val apiError = Gson().fromJson(errorBodyStr, ErrorResponse::class.java)
                    Result.Error(apiError)
                }
            } catch (e: Exception) {
                Log.e("NetworkClient", "Exception: ${e.localizedMessage}", e)
                Result.Error(
                    ErrorResponse(
                        statusCode = -1,
                        error = "Exception",
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            }
        }
    }

    override suspend fun getNowWeatherByPoint(dto: Any): Result<WeatherResponse> {
        if (!isConnected()) {
            return Result.NoConnection
        }

        if (dto !is WeatherRequest) {
            return Result.BadRequest(400)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = yWeatherApi.getNowWeatherByPoint(dto.lat, dto.lon)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.Success(it)
                    } ?: Result.Error(
                        ErrorResponse(
                            statusCode = response.code(),
                            error = "Empty body",
                            message = "Response body is null"
                        )
                    )
                } else {
                    val errorBodyStr = response.errorBody()?.string()
                    val apiError = Gson().fromJson(errorBodyStr, ErrorResponse::class.java)
                    Result.Error(apiError)
                }
            } catch (e: Exception) {
                Log.e("NetworkClient", "Exception: ${e.localizedMessage}", e)
                Result.Error(
                    ErrorResponse(
                        statusCode = -1,
                        error = "Exception",
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            }
        }
    }


    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}