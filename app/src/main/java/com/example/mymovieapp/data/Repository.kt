package com.example.mymovieapp.data

import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository  constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)
    suspend fun insertMoviesToLocal() =
        localDataSource.insertMovie(remoteDataSource.getAllMovies(1).body()!!.results)

    suspend fun insertComingSoon() =
        localDataSource.insertComingSoon(remoteDataSource.getComingSoonMovies(1).body()!!.results)

    fun getComingSoonFromLocal(): Flow<List<ResultX>> {
        return localDataSource.getAllComingSoon()
    }

    fun getFromLocal(): Flow<List<Result>> {
        return localDataSource.getAllMovies()
    }

    suspend fun getMovie(movie_id: Int) = remoteDataSource.getMovie(movie_id)

}