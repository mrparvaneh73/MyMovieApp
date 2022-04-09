package com.example.mymovieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieapp.models.Result
import com.example.mymovieapp.models.ResultX

@Database(entities = [Result::class, ResultX::class], version = 1, exportSchema = false)
public abstract class AppDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context):AppDataBase {
            return INSTANCE ?: synchronized(this) {
                if(INSTANCE !=null){
                    return INSTANCE!!
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}