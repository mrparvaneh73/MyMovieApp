package com.example.mymovieapp.data.remote.network

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException


//object NetworkManager {
//    private var retrofit: Retrofit? = null
//    fun getRetrofitClient(mContext: Context?): MovieApi? {
//        if (retrofit == null) {
//            val oktHttpClient = OkHttpClient.Builder()
//                .addInterceptor(NetworkConnectionInterceptor(mContext!!))
//            // Adding NetworkConnectionInterceptor with okHttpClientBuilder.
////            oktHttpClient.addInterceptor(logging)
//            retrofit = Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(oktHttpClient.build())
//                .build()
//        }
//        return retrofit!!.create(MovieApi::class.java)
//    }
////    val service = retrofit!!.create(MovieApi::class.java)
//}
object NetworkManager {

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service = retrofit.create(MovieApi::class.java)
}



