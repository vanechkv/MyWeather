package com.example.myweather.search.data.network

import okhttp3.Interceptor
import okhttp3.Response

class GeoInterceptor(
    private val apiKey: String,
    private val lang: String,
    private val format: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("lang", lang)
            .addQueryParameter("format", format)
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}