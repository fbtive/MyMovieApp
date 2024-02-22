package com.example.mymovieapp.data

import android.content.Context
import android.content.res.Resources.NotFoundException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.mymovieapp.R
import com.example.mymovieapp.data.remote.api.MovieApi
import com.example.mymovieapp.data.remote.response.MovieDetail
import com.example.mymovieapp.paging.MovieListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MoviesRepository(
    private val context: Context,
    private val movieApi: MovieApi
) {

    fun getMovieList(genre: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory ={ MovieListPagingSource(movieApi, genre)}
        ).flow

    fun getMovieDetail(id: Int ) : Flow<Payload<MovieDetail?>> = flow {
        try {
            val response = movieApi.getMovieDetail(id = id)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Payload.Success(it))
                } ?: throw Exception(context.getString(R.string.error_unknown))
            }else {
                emit(Payload.Error(
                    Exception(context.getString(R.string.error_connectivity))
                ))
            }
        }catch (e: IOException) {
            emit(Payload.Error(
                Exception(context.getString(R.string.error_connectivity))
            ))
        } catch (e: Exception) {
            emit(Payload.Error(
                Exception(context.getString(R.string.error_unknown))
            ))
        }
    }
}