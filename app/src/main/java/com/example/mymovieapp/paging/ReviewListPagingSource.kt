package com.example.mymovieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovieapp.data.remote.api.MovieApi
import com.example.mymovieapp.data.remote.response.Review
import retrofit2.HttpException
import java.io.IOException

class ReviewListPagingSource(
    private val movieApi: MovieApi,
    private val movieId: Int,
): PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val position = params.key ?: 1

        return try {
            val response = movieApi.getMovieReviews(movieId = movieId, page = position)

            val reviews = response.results

            LoadResult.Page(
                data = reviews,
                prevKey = null,
                nextKey = if(position == 500 || reviews.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}