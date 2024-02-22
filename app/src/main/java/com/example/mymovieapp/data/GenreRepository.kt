package com.example.mymovieapp.data

import android.content.Context
import com.example.mymovieapp.R
import com.example.mymovieapp.data.remote.api.MovieApi
import com.example.mymovieapp.data.remote.response.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GenreRepository(private val context: Context, private val movieApi: MovieApi) {

    suspend fun getList(): Flow<Payload<List<Genre>>> = flow {
        try {
            val response = movieApi.getAllGenre()

            if(response.isSuccessful) {
                emit(Payload.Success(response.body()?.genres ?: emptyList() ))
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