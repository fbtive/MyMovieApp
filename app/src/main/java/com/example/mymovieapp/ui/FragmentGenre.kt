package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.adapter.GenreListAdapter
import com.example.mymovieapp.data.remote.response.Genre
import com.example.mymovieapp.databinding.FragmentGenreBinding
import com.example.mymovieapp.viewmodel.GenreViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentGenre : Fragment() {

    private lateinit var binding: FragmentGenreBinding
    private val viewModel by viewModels<GenreViewModel>()
    private lateinit var adapter: GenreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentGenreBinding.inflate(inflater, container, false)

        adapter = GenreListAdapter(this::navigateToMovieList)
        binding.listGenre.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.listGenre.adapter = adapter

        setupObserver()

        return binding.root;
    }

    private fun setupObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if(it)
                binding.loadingBar.visibility = View.VISIBLE
            else
                binding.loadingBar.visibility = View.INVISIBLE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .setAction("REFRESH"){
                    viewModel.refreshGenres()
                }.show()
        }

        viewModel.listGenre.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun navigateToMovieList(genre: Genre) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()

        val bundle = Bundle()
        bundle.putInt("id", genre.id)
        bundle.putString("name", genre.name)

        findNavController().navigate(R.id.fragmentMovieList, bundle, navOptions)
    }

}