package com.example.mymovieapp.data.remote.api

import com.example.mymovieapp.data.remote.response.MovieDetail
import com.example.mymovieapp.data.remote.response.ResponseGenre
import com.example.mymovieapp.data.remote.response.ResponseMovieList
import com.example.mymovieapp.data.remote.response.ResponseReviewList
import com.example.mymovieapp.data.remote.response.ResponseTrailerList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("genre/movie/list")
    suspend fun getAllGenre(@Query("language") language: String = "en"): Response<ResponseGenre>

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("language") language: String = "en",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = true,
        @Query("page") page: Int,
        @Query("with_genres") genre: Int
    ): ResponseMovieList

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int, @Query("language") language: String = "en"): Response<MovieDetail>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Int, @Query("page") page: Int, @Query("language") language: String = "en") : ResponseReviewList

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(@Path("id") movieId: Int, @Query("language") language: String = "en"): Response<ResponseTrailerList>
}

