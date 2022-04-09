package com.example.mymovieapp


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ScrollHandler
import com.example.mymovieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mymovieapp.R


class MainActivity : AppCompatActivity() {
    private val navController by lazy { findNavController(R.id.myNavHostFragment) }

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationUI.setupWithNavController(binding.bottomNav,navController)



    }
}