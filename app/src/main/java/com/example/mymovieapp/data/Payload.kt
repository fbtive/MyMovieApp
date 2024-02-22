package com.example.mymovieapp.data

import androidx.annotation.NonNull

sealed class Payload<out T>() {
    data class Success<out T>( val data: T): Payload<T>()
    data class Error<T>(val exception: Exception): Payload<T>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$exception]"
        }
    }
}