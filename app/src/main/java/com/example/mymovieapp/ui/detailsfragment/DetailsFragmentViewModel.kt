package com.example.mymovieapp.ui.detailsfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.models.MovieDetails
import com.example.mymovieapp.models.Result
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(private val repository: Repository):
    ViewModel() {
    val movieList = MutableLiveData<MovieDetails>()
    fun getMovie(movie_id:Int){
        viewModelScope.launch {
          val response=  repository.getMovie(movie_id)
            if (response.isSuccessful){
                movieList.postValue(response.body())
            }
        }
    }
}