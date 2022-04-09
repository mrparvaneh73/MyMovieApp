package com.example.mymovieapp.ui.detailsfragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.ui.homefragment.HomeFragmentViewModel

class DetailsViewModelFactory(
    private  val repository: Repository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsFragmentViewModel::class.java)) {
            return DetailsFragmentViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}