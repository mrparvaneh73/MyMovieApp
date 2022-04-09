package com.example.mymovieapp.ui.homefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeFragmentViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private var mPageNumber = 1
    private var nexpageNumber=1
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Result>>()
    val comingsoonList = MutableLiveData<List<ResultX>>()
    val nextmovieList = MutableLiveData<List<Result>>()
    val loading = MutableLiveData<Boolean>()
    val searchMovie = MutableLiveData<List<Result>>()
    var job: Job? = null
    private val _listMovies = MutableStateFlow<List<Result>>(emptyList())
        val listMovies:StateFlow<List<Result>> = _listMovies

    private val _listComingSoon = MutableStateFlow<List<ResultX>>(emptyList())
    val listComingSoon:StateFlow<List<ResultX>> = _listComingSoon

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    suspend fun insertComingSoon(){
        repository.insertComingSoon()
    }

    suspend fun insertMovies(){
        repository.insertMoviesToLocal()
    }
    fun getComingSoonFromLocal(){
        viewModelScope.launch {
            repository.getComingSoonFromLocal().collect{
                _listComingSoon.emit(it)
            }
        }
    }
    fun getFromLocal(){
        viewModelScope.launch {
            repository.getFromLocal().collect{
_listMovies.emit(it)
            }
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

//    fun getAllMovies() {
//        viewModelScope.launch {
//            val response = repository.getAllMovies(mPageNumber)
//
//            if (response.isSuccessful) {
//                Log.d(TAG, "getAllMovies: " + response.body())
//                movieList.postValue(response.body()!!.results)
//            }
//
//        }
//    }




//    fun getComingSoonMovies() {
//        viewModelScope.launch {
//            val response = repository.getComingSoonMovies(mPageNumber)
//            if (response.isSuccessful) {
//                Log.d(TAG, "getcomingsoonMovies: " + response.body())
//                comingsoonList.postValue(response.body()!!.results)
//            }
//        }
//    }

//    fun getNextAllMovies() {
//        nexpageNumber += 1
//        viewModelScope.launch {
//            val response=repository.getNextAllMovies(nexpageNumber)
//            if (response.isSuccessful) {
//                val oldResponse=repository.getAllMovies(mPageNumber).body()!!.results as ArrayList<Result>
//                val newResponse=repository.getAllMovies(nexpageNumber).body()!!.results as ArrayList<Result>
//                newResponse.addAll(oldResponse)
//                nextmovieList.postValue(newResponse)
//
//            }
//        }
//    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}