package com.example.mymovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.mymovieapp.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrailersViewModel @Inject constructor(val repository: MoviesRepository): ViewModel() {
    private val _movieId = MutableLiveData<Int>()

    val trailers = _movieId.switchMap {
        repository.getMovieTrailers(it).asLiveData()
    }

    fun setMovieId(value: Int) {
        _movieId.value = value
    }
}