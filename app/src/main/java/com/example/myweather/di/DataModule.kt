package com.example.myweather.di

import com.example.myweather.search.data.network.GeoInterceptor
import com.example.myweather.search.data.network.NetworkClient
import com.example.myweather.search.data.network.RetrofitNetworkClient
import com.example.myweather.search.data.network.WeatherInterceptor
import com.example.myweather.search.data.network.YGeoApi
import com.example.myweather.search.data.network.YWeatherApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val geoClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(GeoInterceptor("0da84552-e64e-4f98-8df8-1b6dbe1babc7", "ru_RU", "json"))
    .build()

private val weatherClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(WeatherInterceptor("fd1e38cc-e812-4a79-9c40-0e8caafdfebe"))
    .build()

val dataModule = module {

    single<YGeoApi> {
        Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru")
            .client(geoClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YGeoApi::class.java)
    }

    single<YWeatherApi> {
        Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru")
            .client(weatherClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YWeatherApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get(), get())
    }
}