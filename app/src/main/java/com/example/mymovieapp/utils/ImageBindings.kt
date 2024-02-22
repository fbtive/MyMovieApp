package com.example.mymovieapp.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mymovieapp.BuildConfig
import com.example.mymovieapp.R

@BindingAdapter("movieImage")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val stringUrl = "${BuildConfig.MEDIA_URL}w220_and_h330_face${imgUrl}"
        val uri = stringUrl.toUri()

        Glide.with(imageView)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.baseline_hide_image_24)
            .into(imageView)
    } ?: imageView.setImageResource(R.drawable.baseline_hide_image_24)
}
