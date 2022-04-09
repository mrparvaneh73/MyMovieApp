package com.example.mymovieapp.ui.homefragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.ComingSoonAdapter
import com.example.mymovieapp.MovieAdapter
import com.example.mymovieapp.NetworkConnection
import com.example.mymovieapp.R
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.local.LocalDataSource
import com.example.mymovieapp.data.local.database.AppDataBase
import com.example.mymovieapp.data.remote.RemoteDataSource
import com.example.mymovieapp.data.remote.network.Service
import com.example.mymovieapp.databinding.FragmentHomeBinding
import com.example.mymovieapp.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var mLayoutManagerHorizontal: RecyclerView.LayoutManager
    private lateinit var viewmodel: HomeFragmentViewModel
    val adapter = MovieAdapter(
        showDetails = { movie ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movie.id
                )
            )
        }
    )
    val adapter2 = ComingSoonAdapter(
        showDetails = { movie ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movie.id
                )
            )
        }
    )
    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "sssssss: " + "helloooo")
        binding = FragmentHomeBinding.bind(view)
        viewmodelprovider()
        val recyclerView = binding.recyclerView
        val recyclerView2 = binding.comingsoonrecyclerView
        mLayoutManagerHorizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.layoutManager = mLayoutManagerHorizontal
        val networkConnection = NetworkConnection(requireContext())

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                lifecycleScope.launch {
                    withContext(Dispatchers.Default){
                        viewmodel.insertMovies()
                        viewmodel.insertComingSoon()
                    }
                }

            } else {
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                // Show No internet connection message
            }
        }
        viewmodel.getComingSoonFromLocal()
        viewmodel.getFromLocal()
        lifecycleScope.launch {
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewmodel.listMovies.collect {
                        adapter.submitList(it)
                        recyclerView.adapter = adapter
                    }
                }
            }
            launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewmodel.listComingSoon.collect{
                        adapter2.submitList(it)
                        recyclerView2.adapter=adapter2
                    }
                }
            }


        }



//        viewmodel.getComingSoonMovies()
//        viewmodel.getAllMovies()
//        viewmodel.comingsoonList.observe(viewLifecycleOwner) {
//            adapter2.submitList(it)
//            recyclerView2.adapter = adapter2
//        }
//        viewmodel.movieList.observe(viewLifecycleOwner) {
//            Log.d(TAG, "onViewCreated: " + "https://image.tmdb.org/t/p/w500${it[0].poster_path}")
//            adapter.submitList(it)
//            recyclerView.adapter = adapter
//

//            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                @SuppressLint("NotifyDataSetChanged")
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    if (!recyclerView.canScrollVertically(1)) {
//                        viewmodel.getNextAllMovies()
//                        viewmodel.nextmovieList.observe(viewLifecycleOwner) {
//                            adapter.submitList(it)
//                            recyclerView.adapter = adapter
//
//                        }
//
//                    }
//                }
//            })

//        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewmodel.searchMovie(query!!)
                viewmodel.searchMovie.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    recyclerView.adapter = adapter
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


    }


    fun viewmodelprovider() {
        val application = requireNotNull(this.activity).application
        val test = Service.getRetrofitClient(requireContext())
        val test2 = test
        val remoteDataSource = RemoteDataSource(Service, requireContext())
        val localDataSource = LocalDataSource(AppDataBase.getDatabase(application).movieDao())
        val repository = Repository(remoteDataSource, localDataSource)

        val factory = HomeViewModelFactory(repository, application)

        viewmodel = ViewModelProvider(this, factory).get(HomeFragmentViewModel::class.java)
    }
}

