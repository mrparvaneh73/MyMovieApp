package com.example.mymovieapp.data

import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
//    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        onError("Exception handled: ${throwable.localizedMessage}")
//    }
    suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)
    suspend fun insertMoviesToLocal() = localDataSource.insertMovie(remoteDataSource.getgetAllMovies(1).body()!!.results)
     suspend fun insertComingSoon()=localDataSource.insertComingSoon(remoteDataSource.getComingSoonMovies(1).body()!!.results)

    fun getComingSoonFromLocal() : Flow<List<ResultX>>{
        return localDataSource.getAllComingSoon()
    }

    fun getFromLocal(): Flow<List<Result>> {
        return localDataSource.getAllMovies()
    }

    suspend fun getAllMovies(page: Int) = remoteDataSource.getgetAllMovies(page)
    suspend fun getComingSoonMovies(page: Int) = remoteDataSource.getComingSoonMovies(page)
    suspend fun getNextAllMovies(page: Int) = remoteDataSource.getgetAllMovies(page)

    suspend fun getMovie(movie_id: Int) = remoteDataSource.getMovie(movie_id)

}