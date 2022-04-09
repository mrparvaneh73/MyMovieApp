package com.example.mymovieapp.ui.homefragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapp.data.Repository

class HomeViewModelFactory(
    private  val repository: Repository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            return HomeFragmentViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}