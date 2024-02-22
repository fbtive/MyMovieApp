package com.example.mymovieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovieapp.data.remote.api.MovieApi
import com.example.mymovieapp.data.remote.response.Movie
import retrofit2.HttpException
import java.io.IOException

class MovieListPagingSource(
    private val movieApi: MovieApi,
    private val genre: Int,
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try {
            val response = movieApi.getMovies(page = position, genre = genre)

            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if(position == 1) null else position - 1,
                nextKey = if(position == 500 || movies.isNullOrEmpty()) null else position +1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}