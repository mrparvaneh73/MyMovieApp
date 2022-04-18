package com.example.mymovieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX

@Database(entities = [Result::class, ResultX::class], version = 1, exportSchema = false)
 abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}