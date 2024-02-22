package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mymovieapp.R
import com.example.mymovieapp.data.Payload
import com.example.mymovieapp.databinding.FragmentMovieDetailBinding
import com.example.mymovieapp.utils.formatSimpleDate
import com.example.mymovieapp.viewmodel.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMovieDetail : Fragment() {

    private lateinit var binding:FragmentMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        initUI()
        setupObserver()

        return binding.root
    }

    private fun initUI(){
        val bundle = requireArguments()
        viewModel.setMovieId(bundle.getInt("id"))
    }

    private fun setupObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loadingBar.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }

        viewModel.movie.observe(viewLifecycleOwner) {data ->
            data?.let {
                binding.movie = it
                binding.movieReleaseDate.text = formatSimpleDate(it.releaseDate)
            }
        }

        viewModel.eventError.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                .setAction("REFRESH") {
                    viewModel.refreshMovieDetail()
                }.show()
        }
    }

}