package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.adapter.MovieListAdapter
import com.example.mymovieapp.adapter.GeneralLoadStateAdapter
import com.example.mymovieapp.data.remote.response.Movie
import com.example.mymovieapp.databinding.FragmentMovieListBinding
import com.example.mymovieapp.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMovieList : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel by viewModels<MovieViewModel>()
    private lateinit var adapter: MovieListAdapter
    private lateinit var headerAdapter: GeneralLoadStateAdapter
    private lateinit var footerAdapter: GeneralLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        initUI()
        setupObserver()

        return binding.root
    }

    private fun initUI() {
        val bundle = requireArguments()
        val id = bundle.getInt("id")
        val name = bundle.getString("name")

        binding.headline.text = requireContext().getString(R.string.genre_healine, name)
        viewModel.setGenre(id)

        adapter = MovieListAdapter(this::navigateToMovieDetail)
        headerAdapter = GeneralLoadStateAdapter { adapter.retry() }
        footerAdapter = GeneralLoadStateAdapter { adapter.retry() }

        binding.apply {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if((position == adapter.itemCount && footerAdapter.itemCount > 0)
                        || (position == 0 && headerAdapter.itemCount > 0))
                            2
                        else 1
                }

            })
            movieList.layoutManager = gridLayoutManager
            movieList.setHasFixedSize(true)
            movieList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
                footer = footerAdapter
            )
        }

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        adapter.addLoadStateListener { loadState ->

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
        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    fun navigateToMovieDetail(movie: Movie) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .build()

        val bundle = Bundle()
        bundle.putInt("id", movie.id)

        findNavController().navigate(R.id.fragmentMovieDetail, bundle, navOptions)
    }
}