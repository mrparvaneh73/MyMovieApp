package com.example.mymovieapp.ui.homefragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.ComingSoonAdapter
import com.example.mymovieapp.MovieAdapter
import com.example.mymovieapp.utils.NetworkConnection
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentHomeBinding
import com.example.mymovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var mLayoutManagerHorizontal: RecyclerView.LayoutManager

    val viewmodel by viewModels<HomeFragmentViewModel>()

    val adapter = MovieAdapter(showDetails = { movie ->
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                movie.id
            )
        )
    })
    val adapter2 = ComingSoonAdapter(showDetails = { movie ->
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                movie.id
            )
        )
    })
    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        checkingConnection()
        init()

    }

    fun init() = binding.apply {


        mLayoutManagerHorizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.comingsoonrecyclerView.layoutManager = mLayoutManagerHorizontal


    }

    fun checkingConnection() {
        val networkConnection = NetworkConnection(requireContext())

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {

                getFromNetwork()

                search()

            } else {
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                getFromLocal()
            }
        }

    }

    fun getFromNetwork() = binding.apply {
        viewmodel.getMovieList(1)
        viewmodel.getComingSoon(1)
        lifecycleScope.launchWhenStarted {
            launch {
                viewmodel.listMovies.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            Log.d("miladmovie", "getFromNetwork: "+it.data.toString())
                            adapter.submitList(it.data)
                            binding.recyclerView.adapter = adapter
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {

                        }
                    }

                }
            }
            launch {

                viewmodel.listComingSoon.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            Log.d("miladcomingsoon", "getFromNetwork: "+it.data.toString())
                            adapter2.submitList(it.data)
                            binding.comingsoonrecyclerView.adapter = adapter2
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }


        }

    }

    fun getFromLocal() {
        viewmodel.getMovieFromLocal()
        viewmodel.getComingSoonFromLocal()
        lifecycleScope.launchWhenStarted {
            launch {
                viewmodel.listLocaMovies.collectLatest {
                    adapter.submitList(it)
                    binding.recyclerView.adapter = adapter
                }
            }
            launch {
                viewmodel.listLocalComingSoon.collectLatest {
                    adapter2.submitList(it)
                    binding.comingsoonrecyclerView.adapter = adapter2
                }
            }


        }

    }

    fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewmodel.searchMovie(query!!)
                viewmodel.searchMovie.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.recyclerView.adapter = adapter
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }


}

