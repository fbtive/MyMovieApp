package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymovieapp.data.remote.response.Genre
import com.example.mymovieapp.databinding.ListItemGenreBinding

class GenreListAdapter(val clickListener: (Genre) -> Unit): ListAdapter<Genre, GenreListAdapter.GenreViewHolder>(GenreDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        return holder.bind(getItem(position), clickListener)
    }

    class GenreViewHolder(val binding: ListItemGenreBinding): ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): GenreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemGenreBinding.inflate(inflater, parent, false)

                return GenreViewHolder(binding)
            }
        }

        fun bind(item: Genre, clickListener: (Genre) -> Unit) {
            binding.genreTitle.text = item.name
            binding.genreItem.setOnClickListener { clickListener(item) }
        }
    }

    class GenreDiffUtil: DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }
    }
}