package com.example.mymovieapp.ui.detailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.Repository
import com.example.mymovieapp.models.MovieDetails
import com.example.mymovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _listMovies = MutableSharedFlow<Resource<MovieDetails>>()
    val listMovies: SharedFlow<Resource<MovieDetails>> = _listMovies

    fun getMovie(movie_id: Int) {
        viewModelScope.launch {
            repository.getMovie(movie_id).collectLatest {
                _listMovies.emit(it)
            }

        }
    }
}