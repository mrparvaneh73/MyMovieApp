package com.example.mymovieapp.data.remote

import android.content.Context
import com.example.mymovieapp.data.remote.network.Service

class RemoteDataSource(private val service:Service,private val context:Context) {
    suspend fun searchMovie(query:String)=service.getRetrofitClient(context)!!.searchMovie(query)

    suspend fun getgetAllMovies(page:Int)=service.getRetrofitClient(context)!!.getAllMovies(page)
    suspend fun getComingSoonMovies(page:Int)=service.getRetrofitClient(context)!!.getComingSoonMovies(page)
    suspend fun getMovie(movie_id:Int)=service.getRetrofitClient(context)!!.getMovie(movie_id)
}