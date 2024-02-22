package com.example.mymovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.MoviesRepository
import com.example.mymovieapp.data.Payload
import com.example.mymovieapp.data.remote.response.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository): ViewModel() {

    private var job: Job? = null

    private val _movieId = MutableLiveData<Int>()

    private val _movie = MutableLiveData<MovieDetail?>()
    val movie : LiveData<MovieDetail?>
        get() = _movie

    private val _eventError = MutableLiveData<Exception>()
    val eventError: LiveData<Exception>
        get() = _eventError

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun setMovieId(value: Int) {
        _movieId.value = value

        refreshMovieDetail()
    }

    fun refreshMovieDetail() {
        _movieId.value?.let {
            _loading.value = true
            job?.cancel()
            job = viewModelScope.launch {
                repository.getMovieDetail(it)
                    .flowOn(Dispatchers.IO)
                    .collect {payload ->
                        when (payload) {
                            is Payload.Success -> {
                                _movie.value = payload.data
                            }
                            is Payload.Error -> {
                                _eventError.value = payload.exception
                            }
                        }

                        _loading.value = false
                    }
            }
        }
    }
}