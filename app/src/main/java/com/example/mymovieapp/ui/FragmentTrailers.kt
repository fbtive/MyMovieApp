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
import com.example.mymovieapp.adapter.TrailerListAdapter
import com.example.mymovieapp.data.Payload
import com.example.mymovieapp.data.remote.response.Trailer
import com.example.mymovieapp.databinding.FragmentTrailersBinding
import com.example.mymovieapp.viewmodel.TrailersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTrailers : Fragment() {

    private lateinit var binding: FragmentTrailersBinding
    private val viewModel by viewModels<TrailersViewModel>()
    private lateinit var adapter: TrailerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrailersBinding.inflate(inflater, container, false)

        initUI()
        setupObserver()

        return binding.root
    }

    private fun initUI() {
        val bundle = requireArguments()
        val movieId = bundle.getInt("id")

        viewModel.setMovieId(movieId)

        adapter = TrailerListAdapter(this::navigateToYoutubePlayer)

        binding.trailerList.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.trailers.observe(viewLifecycleOwner) {
            when(it) {
                is Payload.Success -> {
                    adapter.submitList(it.data)
                }
                is Payload.Error -> {
                    Snackbar.make(binding.root, it.exception.message.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("REFRESH") {
                            viewModel.setMovieId(requireArguments().getInt("id"))
                        }.show()
                }
            }
        }
    }

    private fun navigateToYoutubePlayer(trailer: Trailer) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_bottom)
            .setExitAnim(R.anim.fade_out)
            .setPopExitAnim(R.anim.slide_out_bottom)
            .setPopEnterAnim(R.anim.fade_in)
            .build()

        val bundle = Bundle()
        bundle.putString("key", trailer.key)

        findNavController().navigate(R.id.fragmentYtPlayer, bundle, navOptions)
    }
}