package com.example.mymovieapp.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*


import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieList(vararg result: Result)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComingSoonList(vararg result: ResultX)

    @Query("SELECT * FROM comingSoon")
   suspend fun getAllComingSoon(): List<ResultX>

    @Query("SELECT * FROM movie")
   suspend fun getAllMovies(): List<Result>

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertMovieDetails(movieDetails: MovieDetails)

//    @Transaction
//    @Query("SELECT * FROM movie WHERE id = :id")
//    fun getUserAndTodos(id: Int): Flow<MovieAndDetails>

}