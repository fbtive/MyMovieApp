package com.example.mymovieapp.data.remote.response

data class ResponseMovieList(
    val page: Int,
    val results: List<Movie>
)