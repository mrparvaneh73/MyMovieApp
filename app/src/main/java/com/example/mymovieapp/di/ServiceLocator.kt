package com.example.mymovieapp.di

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.local.database.AppDataBase
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.data.remote.network.NetworkManager

class ServiceLocator(application: Application) {
    val remoteDataSource = RemoteDataSource(NetworkManager.service)
    val localDataSource= LocalDataSource(AppDataBase.getDatabase(application).movieDao())
    val repository = Repository(remoteDataSource,localDataSource)
}