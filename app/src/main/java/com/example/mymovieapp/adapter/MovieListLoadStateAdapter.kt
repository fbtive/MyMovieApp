package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymovieapp.databinding.ListItemUiLoadingBinding

class MovieListLoadStateAdapter(private val onClickRetry: () -> Unit):
    LoadStateAdapter<MovieListLoadStateAdapter.MovieLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, onClickRetry)
    }

    class MovieLoadStateViewHolder(val binding: ListItemUiLoadingBinding): ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup) : MovieLoadStateViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemUiLoadingBinding.inflate(inflater, parent, false)

                return MovieLoadStateViewHolder(binding)
            }
        }

        fun bind(loadState: LoadState, onClickRetry: () -> Unit) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState !is LoadState.Loading
            binding.textViewError.isVisible = loadState !is LoadState.Loading
            binding.buttonRetry.setOnClickListener{ onClickRetry() }
        }
    }
}