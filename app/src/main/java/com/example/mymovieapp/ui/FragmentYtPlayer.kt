package com.example.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentYtPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class FragmentYtPlayer : Fragment() {

    private lateinit var binding: FragmentYtPlayerBinding
    private lateinit var youTubePlayer: YouTubePlayer

    private var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                // if the player is in fullscreen, exit fullscreen
                youTubePlayer.toggleFullscreen()
            } else {
                findNavController().navigateUp()
                toggleSystemUI(false)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYtPlayerBinding.inflate(inflater, container, false)

        initUI()

        return binding.root;
    }

    private fun initUI() {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        toggleSystemUI(true)

        val videoId = requireArguments().getString("key")

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()
        binding.youtubePlayerView.enableAutomaticInitialization = false

        binding.youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true
                binding.youtubePlayerView.visibility = View.GONE
                binding.fullScreenViewContainer.visibility = View.VISIBLE
                binding.fullScreenViewContainer.addView(fullscreenView)
            }

            override fun onExitFullscreen() {
                isFullscreen = false
                binding.youtubePlayerView.visibility = View.VISIBLE
                binding.fullScreenViewContainer.visibility = View.GONE
                binding.fullScreenViewContainer.removeAllViews()
            }
        })

        binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@FragmentYtPlayer.youTubePlayer = youTubePlayer
                youTubePlayer.loadVideo(videoId!!, 0f)

            }
        }, iFramePlayerOptions)
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    private fun toggleSystemUI(hide: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.youtubePlayerView).let {
                controller: WindowInsetsControllerCompat ->

            if(hide)
                controller.hide(WindowInsetsCompat.Type.systemBars())
            else
                controller.show(WindowInsetsCompat.Type.systemBars())

            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}