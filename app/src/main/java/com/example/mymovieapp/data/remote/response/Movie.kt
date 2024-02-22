package com.example.mymovieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
)