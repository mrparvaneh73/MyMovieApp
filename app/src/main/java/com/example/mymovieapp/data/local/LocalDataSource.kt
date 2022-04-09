package com.example.mymovieapp.data.local

import com.example.mymovieapp.data.local.database.MovieDao
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    suspend fun insertMovie(movies:List<Result>){
        movieDao.insertMovieList(*movies.toTypedArray())
    }
    suspend fun insertComingSoon(movies:List<ResultX>){
        movieDao.insertComingSoonList(*movies.toTypedArray())
    }
    fun getAllComingSoon():Flow<List<ResultX>>{
        return  movieDao.getAllComingSoon()
    }

    fun getAllMovies():Flow<List<Result>>{
       return  movieDao.getAllMovies()
    }
}