package com.example.mymovieapp.data.local

import com.example.mymovieapp.data.local.database.MovieDao
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    suspend fun insertMovie(movies:List<Result>){
        movieDao.insertMovieList(*movies.toTypedArray())
    }
    suspend fun insertComingSoon(movies:List<ResultX>){
        movieDao.insertComingSoonList(*movies.toTypedArray())
    }
 suspend   fun getAllComingSoon():List<ResultX>{
        return  movieDao.getAllComingSoon()
    }

   suspend fun getAllMovies():List<Result>{
       return  movieDao.getAllMovies()
    }
}