package com.example.mymovieapp.data.repositories

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.models.MovieDetails
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import com.example.mymovieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun searchMovie(query: String) = remoteDataSource.searchMovie(query)

    fun getMovieList(page: Int): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteMovieList = remoteDataSource.getAllMovies(page).body()!!.results
            localDataSource.insertMovie(remoteMovieList)
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }
        val localMovieList = localDataSource.getAllMovies()
        emit(Resource.Success(localMovieList))

    }

    fun getComingSoonList(page: Int): Flow<Resource<List<ResultX>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteMovieList = remoteDataSource.getComingSoonMovies(page).body()!!.results
            Log.d("repoooooo", "getComingSoonList: "+remoteMovieList.toString())
            localDataSource.insertComingSoon(remoteMovieList)
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }
        val localMovieList = localDataSource.getAllComingSoon()
        Log.d("repoooooo2", "getComingSoonList: "+localMovieList.toString())
        emit(Resource.Success(localMovieList))

    }


    suspend fun getComingSoonFromLocal(): List<ResultX> {
        return localDataSource.getAllComingSoon()
    }

    suspend fun getFromLocal(): List<Result> {
        return localDataSource.getAllMovies()
    }

    fun getMovie(movie_id: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading())
        try {
            val remoteMovieList = remoteDataSource.getMovie(movie_id).body()!!
            emit(Resource.Success(remoteMovieList))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }


    }


}