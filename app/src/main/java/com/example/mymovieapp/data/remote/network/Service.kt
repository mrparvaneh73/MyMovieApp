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


object Service {
    private var retrofit: Retrofit? = null
    fun getRetrofitClient(mContext: Context?): MovieApi? {
        if (retrofit == null) {
            val oktHttpClient = OkHttpClient.Builder()
                .addInterceptor(NetworkConnectionInterceptor(mContext!!))
            // Adding NetworkConnectionInterceptor with okHttpClientBuilder.
//            oktHttpClient.addInterceptor(logging)
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(oktHttpClient.build())
                .build()
        }
        return retrofit!!.create(MovieApi::class.java)
    }
//    val service = retrofit!!.create(MovieApi::class.java)
}
//object Service {
//
//    val client = OkHttpClient.Builder().
//        addInterceptor(NetworkConnectionInterceptor() )
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        })
//        .build()
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.themoviedb.org")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    val service = retrofit.create(MovieApi::class.java)
//}
//class ConnectivityAwareUrlClient(
//    wrappedClient: com.sun.security.ntlm.Client,
//    ncm: NetworkConnectivityManager
//) :
//    com.sun.security.ntlm.Client {
//    var log: HttpLoggingInterceptor.Logger = com.sun.org.slf4j.internal.LoggerFactory.getLogger(
//        ConnectivityAwareUrlClient::class.java
//    )
//    var wrappedClient: com.sun.security.ntlm.Client
//    private val ncm: NetworkConnectivityManager
//    @Throws(IOException::class)
//    fun execute(request: Request?): Response {
//        if (!ncm.isConnected()) {
//            log.debug("No connectivity %s ", request)
//            throw NoConnectivityException("No connectivity")
//        }
//        return wrappedClient.execute(request)
//    }
//
//    init {
//        this.wrappedClient = wrappedClient
//        this.ncm = ncm
//    }
//}
//class ConnectivityInterceptor(isNetworkActive: Observable<Boolean?>) :
//    Interceptor {
//    private val isNetworkActive = false
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        return if (!isNetworkActive) {
//            throw NoConnectivityException()
//        } else {
//            chain.proceed(chain.request())
//        }
//    }
//
//    init {
//
//        isNetworkActive.subscribe(
//            { _isNetworkActive -> this.isNetworkActive = _isNetworkActive }
//        ) { _error -> Log.e("NetworkActive error " + _error.getMessage()) }
//    }
//}
//
//class NoConnectivityException : IOException() {
//    override val message: String
//        get() = "No network available, please check your WiFi or Data connection"
//}
class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            Log.d(TAG, "intercept: ")
//            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    fun isConnected(): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    init {
        mContext = context
    }
}
class NoConnectivityException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}

