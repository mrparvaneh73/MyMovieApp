package com.example.mymovieapp.ui.homefragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.Repository
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import com.example.mymovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val searchMovie = MutableLiveData<List<Result>>()
    var job: Job? = null

    private val _listMovies = MutableStateFlow<Resource<List<Result>>>(Resource.Success(emptyList()))
    val listMovies: StateFlow<Resource<List<Result>>> = _listMovies

    private val _listComingSoon = MutableStateFlow<Resource<List<ResultX>>>(Resource.Success(emptyList()))
    val listComingSoon: StateFlow<Resource<List<ResultX>>> = _listComingSoon

    private val _listLocalMovies = MutableStateFlow<List<Result>>(emptyList())
    val listLocaMovies: StateFlow<List<Result>> = _listLocalMovies

    private val _listLocalComingSoon = MutableStateFlow<List<ResultX>>(emptyList())
    val listLocalComingSoon: StateFlow<List<ResultX>> = _listLocalComingSoon

    fun getMovieList(page: Int) = viewModelScope.launch {
        repository.getMovieList(page).collect {
            _listMovies.value = it
        }
    }

    fun getComingSoon(page: Int) = viewModelScope.launch {
        repository.getComingSoonList(page).collect {
            _listComingSoon.value = it
            Log.d("sssssss", "getComingSoon: "+it.data.toString())
        }
    }

    fun getComingSoonFromLocal() {
        viewModelScope.launch {

            _listLocalComingSoon.value = repository.getComingSoonFromLocal()

        }
    }

    fun getMovieFromLocal() {
        viewModelScope.launch {
            _listLocalMovies.value = repository.getFromLocal()
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val response = repository.searchMovie(query)
            withContext(Main) {
                if (response.isSuccessful) {
                    searchMovie.postValue(response.body()!!.results)
                }
            }

        }


    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}