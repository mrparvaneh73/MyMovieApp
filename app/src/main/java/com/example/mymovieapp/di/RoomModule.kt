package com.example.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun dataBase(@ApplicationContext context: Context): MovieDatabase = Room.databaseBuilder(context,
        MovieDatabase::class.java,"user").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun dao(db:MovieDatabase)=db.movieDao()
}