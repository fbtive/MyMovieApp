package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymovieapp.data.remote.response.Trailer
import com.example.mymovieapp.databinding.ListItemTrailersBinding

class TrailerListAdapter(private val clickListener: (Trailer) -> Unit)
    : ListAdapter<Trailer, TrailerListAdapter.TrailerViewHolder>(TrailerDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class TrailerViewHolder(val binding: ListItemTrailersBinding): ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup) : TrailerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrailersBinding.inflate(inflater, parent, false)

                return TrailerViewHolder(binding)
            }
        }

        fun bind(item: Trailer, clickListener: (Trailer) -> Unit) {
            binding.title.text = item.name
            binding.trailerItem.setOnClickListener { clickListener(item) }
        }
    }

    class TrailerDiffUtil: DiffUtil.ItemCallback<Trailer>() {
        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem.id == newItem.id
        }
    }
}