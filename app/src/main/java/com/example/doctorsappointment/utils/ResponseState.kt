package com.example.doctorsappointment.utils

sealed class ResponseState<out T> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val errorMessage: String) : ResponseState<String>()
    data object Loading : ResponseState<Nothing>()
}