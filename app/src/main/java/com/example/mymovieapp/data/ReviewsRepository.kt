package com.example.mymovieapp.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.mymovieapp.data.remote.api.MovieApi
import com.example.mymovieapp.paging.ReviewListPagingSource

class ReviewsRepository (private val context: Context, private val movieApi: MovieApi) {

    fun getReviews(movieId: Int) = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ReviewListPagingSource(movieApi, movieId) }
    ).flow
}