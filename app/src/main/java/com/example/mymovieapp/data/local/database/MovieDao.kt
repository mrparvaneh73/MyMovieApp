package com.example.mymovieapp.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(vararg result:Result)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComingSoonList(vararg result: ResultX)

    @Query("SELECT * FROM movie")
    fun getAllComingSoon(): Flow<List<ResultX>>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<Result>>
}