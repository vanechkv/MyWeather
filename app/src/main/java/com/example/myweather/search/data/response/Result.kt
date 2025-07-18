package com.example.myweather.search.data.response

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Error(val errorResponse: ErrorResponse): Result<Nothing>()
    object NoConnection: Result<Nothing>()
    data class BadRequest(val code: Int): Result<Nothing>()
}