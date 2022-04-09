package com.example.mymovieapp.ui.playerfragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.ui.homefragment.HomeFragmentViewModel

class PlayerViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            return PlayerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}