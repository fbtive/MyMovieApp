package com.example.mymovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mymovieapp.data.ReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor( private val repository: ReviewsRepository): ViewModel() {

    private val _movieId = MutableLiveData<Int>()

    val reviews = _movieId.switchMap {
        repository.getReviews(it).cachedIn(viewModelScope).asLiveData()
    }

    fun setMovieId(value: Int) {
        _movieId.value = value
    }
}