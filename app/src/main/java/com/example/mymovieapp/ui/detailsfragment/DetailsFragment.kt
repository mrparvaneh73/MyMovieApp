package com.example.mymovieapp.ui.detailsfragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentDetailsBinding
import com.example.mymovieapp.utils.NetworkConnection
import com.example.mymovieapp.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewmodel by viewModels<DetailsFragmentViewModel>()

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        setBottomSheet()

        getBackToHome()

        checkingConnection()
    }

    fun checkingConnection() {
        val networkConnection = NetworkConnection(requireContext())

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                showDetails()

            } else {
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()

            }
        }

    }

    fun showDetails() {
        viewmodel.getMovie(args.movieid)
        lifecycleScope.launchWhenStarted {
            viewmodel.listMovies.collectLatest {

                when (it) {

                    is Resource.Success -> {
                        binding.textViewTitleDetails.text = it.data!!.title
                        Glide.with(requireContext())
                            .load("https://image.tmdb.org/t/p/w500${it.data.poster_path}")
                            .into(binding.imageviewDetails)
                        binding.ratingBarDetails.rating = (it.data.vote_average.toFloat()) / 2
                        binding.textViewDesDetails.text = it.data.overview
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Error -> {

                    }
                }

            }
        }


    }

    fun getBackToHome() {
        binding.backbutton.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }
    }

    fun setBottomSheet() {
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 500
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


}