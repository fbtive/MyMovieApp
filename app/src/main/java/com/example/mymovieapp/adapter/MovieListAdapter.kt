package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymovieapp.R
import com.example.mymovieapp.data.remote.response.Movie
import com.example.mymovieapp.databinding.ListItemMovieBinding
import com.example.mymovieapp.utils.formatSimpleDate

class MovieListAdapter (private val clickListener: (Movie)->Unit)
    : PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position) as Movie, clickListener)
    }
    class MovieViewHolder(val binding: ListItemMovieBinding) : ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding)
            }
        }

        fun bind(item: Movie, clickListener: (Movie) -> Unit) {
            binding.movieItem.setOnClickListener { clickListener(item) }
            binding.movie = item
            binding.movieDate.text = formatSimpleDate(item.releaseDate)

            binding.executePendingBindings()
        }
    }

    class MovieDiffUtil: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}