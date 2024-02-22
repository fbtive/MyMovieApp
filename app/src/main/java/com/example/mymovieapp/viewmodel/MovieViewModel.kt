package com.example.mymovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymovieapp.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val repository: MoviesRepository) : ViewModel() {

    private val _genre = MutableLiveData<Int>()
    val genre: LiveData<Int>
        get() = _genre

    val movieList =_genre.switchMap {
        repository.getMovieList(it).cachedIn(viewModelScope).asLiveData()
    }


    fun setGenre(value: Int) {
        _genre.value = value
    }
}