package com.example.mymovieapp.di

import com.example.mymovieapp.data.repositories.Repository
import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providesMainRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
    ): Repository {
        return Repository(remoteDataSource, localDataSource)
    }
}