package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.example.mymovieapp.R
import com.example.mymovieapp.adapter.GeneralLoadStateAdapter
import com.example.mymovieapp.adapter.MovieReviewListAdapter
import com.example.mymovieapp.databinding.FragmentMovieReviewsBinding
import com.example.mymovieapp.viewmodel.MovieReviewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMovieReviews : Fragment() {

    private lateinit var binding: FragmentMovieReviewsBinding
    private val viewModel by viewModels<MovieReviewsViewModel>()

    private lateinit var adapter: MovieReviewListAdapter
    private lateinit var footerAdapter: GeneralLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieReviewsBinding.inflate(inflater, container, false)

        initUI()
        setupObserver()

        return binding.root
    }

    private fun initUI() {
        val bundle = requireArguments()
        val id = bundle.getInt("id")
        val name = bundle.getString("name")

        binding.headline.text = getString(R.string.movie_review_headline, name)

        viewModel.setMovieId(id)

        adapter = MovieReviewListAdapter()
        footerAdapter = GeneralLoadStateAdapter { adapter.retry() }
        binding.reviewList.adapter = adapter.withLoadStateFooter(footerAdapter)

        adapter.addLoadStateListener {loadState ->
            binding.loadingBar.visibility = if(loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.INVISIBLE

            if(loadState.source.refresh is LoadState.Error) {
                Snackbar.make(binding.root, getString(R.string.error_unknown), Snackbar.LENGTH_SHORT)
                    .setAction("REFRESH") {
                        adapter.refresh()
                    }.show()
            }
        }

    }

    private fun setupObserver() {
        viewModel.reviews.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

}