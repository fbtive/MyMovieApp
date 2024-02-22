package com.example.mymovieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val budget: Long,
    @SerializedName("poster_path")
    val posterPath: String,
)
