package com.example.myweather.search.data.response

data class ErrorResponse(
    val statusCode: Int,
    val error: String,
    val message: String
)