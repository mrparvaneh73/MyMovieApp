package com.example.mymovieapp.ui.detailsfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.local.database.AppDataBase
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.data.remote.network.Service
import com.example.mymovieapp.databinding.FragmentDetailsBinding
import com.example.mymovieapp.ui.homefragment.HomeFragmentViewModel
import com.example.mymovieapp.ui.homefragment.HomeViewModelFactory

class DetailsFragment:Fragment() {
    private lateinit var viewmodel:DetailsFragmentViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding:FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailsBinding.inflate(inflater,container,false)
        viewmodelprovider()
        Log.d("details", args.movieid.toString())
        viewmodel.getMovie(args.movieid)
        viewmodel.movieList.observe(viewLifecycleOwner){
            binding.textViewTitleDetails.text=it.title
            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500${it.poster_path}").into(binding.imageviewDetails)
            binding.ratingBarDetails.rating=(it.vote_average.toFloat())/2
            binding.textViewDesDetails.text=it.overview
        }

        return binding.root
    }


    fun viewmodelprovider() {
        val application = requireNotNull(this.activity).application

        val remoteDataSource = RemoteDataSource(Service,requireContext())
        val localDataSource= LocalDataSource(AppDataBase.getDatabase(application).movieDao())
        val repository = Repository(remoteDataSource,localDataSource)

        val factory = DetailsViewModelFactory(repository, application)

        viewmodel = ViewModelProvider(this, factory).get(DetailsFragmentViewModel::class.java)
    }
}