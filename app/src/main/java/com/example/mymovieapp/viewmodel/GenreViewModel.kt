package com.example.mymovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.GenreRepository
import com.example.mymovieapp.data.Payload
import com.example.mymovieapp.data.remote.response.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val repository: GenreRepository): ViewModel() {
    private var job: Job? = null

    private val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>>
        get() = _listGenre

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        refreshGenres()
    }

    fun refreshGenres() {
        job?.cancel()

        _loading.value = true

        job = viewModelScope.launch {
            repository.getList()
                .flowOn(Dispatchers.IO)
                .collect {
                    when(it) {
                        is Payload.Success -> {
                            _listGenre.value = it.data!!
                        }
                        is Payload.Error -> {
                            _errorMessage.value = it.exception.message
                        }
                    }

                    _loading.value = false
                }
        }
    }
}