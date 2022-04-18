package com.example.mymovieapp.data.remote.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//object NetworkManager {
//    private var retrofit: Retrofit? = null
//    fun getRetrofitClient(mContext: Context?): MovieService? {
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
//        return retrofit!!.create(MovieService::class.java)
//    }
////    val service = retrofit!!.create(MovieService::class.java)
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

    val service = retrofit.create(MovieService::class.java)
}



