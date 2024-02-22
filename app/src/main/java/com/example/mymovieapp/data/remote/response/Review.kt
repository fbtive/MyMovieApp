package com.example.mymovieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Review(
    val id: String,
    val author: String,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
)