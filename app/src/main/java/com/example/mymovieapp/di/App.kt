package com.example.mymovieapp.di

import android.app.Application

class App: Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator=ServiceLocator(this)
    }

}