package com.example.mymovieapp.data.remote

import com.example.mymovieapp.data.remote.network.MovieService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service:MovieService) {
    suspend fun searchMovie(query:String)=service.searchMovie(query)

    suspend fun getAllMovies(page:Int)=service.getAllMovies(page)
    suspend fun getComingSoonMovies(page:Int)=service.getComingSoonMovies(page=page)
    suspend fun getMovie(movie_id:Int)=service.getMovie(movie_id)
}