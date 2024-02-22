package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mymovieapp.data.remote.response.Review
import com.example.mymovieapp.databinding.ListItemReviewBinding
import com.example.mymovieapp.utils.formatIsoDateToDateTime

class MovieReviewListAdapter: PagingDataAdapter<Review, MovieReviewListAdapter.MovieReviewViewHolder>(MovieReviewDiffUtil()) {

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        holder.bind(getItem(position) as Review)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
        return MovieReviewViewHolder.from(parent)
    }

    class MovieReviewViewHolder(val binding: ListItemReviewBinding): ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MovieReviewViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemReviewBinding.inflate(inflater, parent, false)

                return MovieReviewViewHolder(binding)
            }
        }

        fun bind(item: Review) {
            binding.review = item
            binding.textviewDate.text = formatIsoDateToDateTime(item.createdAt)

            binding.executePendingBindings()
        }
    }

    class MovieReviewDiffUtil: DiffUtil.ItemCallback<Review>() {

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

    }
}