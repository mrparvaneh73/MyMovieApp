package com.example.mymovieapp.data.remote.network

import com.example.mymovieapp.models.Comingsoon
import com.example.mymovieapp.models.MovieDetails
import com.example.mymovieapp.models.PopularMovies
import com.example.mymovieapp.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieService {
    @GET("3/search/movie")
   suspend fun searchMovie(
        @Query("query") query:String,
        @Query("api_key") api_key:String=Constants.API_KEY
   ):Response<PopularMovies>

    @GET("3/movie/popular")
    suspend fun getAllMovies(
        @Query("page") page:Int,
        @Query("api_key") api_key:String=Constants.API_KEY
    ) : Response<PopularMovies>

    @GET("3/movie/upcoming")
    suspend fun getComingSoonMovies(
        @Query("page") page:Int,
        @Query("api_key") api_key:String=Constants.API_KEY
    ) : Response<Comingsoon>

    @GET("3/movie/{movie_id}?")
    suspend fun getMovie(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Constants.API_KEY
    ) :Response<MovieDetails>

}