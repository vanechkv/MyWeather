package com.example.myweather.search.data.network

import okhttp3.Interceptor
import okhttp3.Response

class WeatherInterceptor(
    private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHeaders = original.headers()

        val newHeaders = originalHeaders.newBuilder()
            .add("X-Yandex-Weather-Key", apiKey)
            .build()

        val newRequest = original.newBuilder()
            .headers(newHeaders)
            .build()

        return chain.proceed(newRequest)
    }
}