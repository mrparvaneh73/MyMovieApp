package com.example.mymovieapp.data.remote

import com.example.mymovieapp.data.remote.network.MovieApi
import com.example.mymovieapp.data.remote.network.NetworkManager

class RemoteDataSource(private val service:MovieApi) {
    suspend fun searchMovie(query:String)=service.searchMovie(query)

    suspend fun getAllMovies(page:Int)=service.getAllMovies(page)
    suspend fun getComingSoonMovies(page:Int)=service.getComingSoonMovies(page)
    suspend fun getMovie(movie_id:Int)=service.getMovie(movie_id)
}