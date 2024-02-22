package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
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
                binding.reviewButton.setOnClickListener {
                    navigateToMovieReview(data.id, data.title)
                }
            }
        }

        viewModel.eventError.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                .setAction("REFRESH") {
                    viewModel.refreshMovieDetail()
                }.show()
        }
    }

    private fun navigateToMovieReview(id: Int, title: String) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .build()

        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("name", title)

        findNavController().navigate(R.id.fragmentMovieReviews, bundle, navOptions)

    }

}