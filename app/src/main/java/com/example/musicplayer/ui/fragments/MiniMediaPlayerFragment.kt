package com.example.musicplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.R
import com.example.musicplayer.controller.MediaPlayerController
import com.example.musicplayer.databinding.MiniMediaPlayerBinding
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.service.MediaService
import com.example.musicplayer.ui.MainActivity
import com.example.musicplayer.ui.viewmodel.MediaPlayerViewModel

class MiniMediaPlayerFragment : Fragment(R.layout.mini_media_player) {

    private lateinit var binding: MiniMediaPlayerBinding
    private val viewModel: MediaPlayerViewModel by activityViewModels()
    private var mediaService: MediaService? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mediaService = (activity as MainActivity).getMediaService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  MiniMediaPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniPlayerRootView.setOnClickListener {
            MediaPlayerController.getInstance().switchMiniToFullPlayer()
        }

        binding.playPauseBtn.setOnClickListener {
            mediaService?.let { service ->
                viewModel.playPause(service)
            }
        }
        observeMediaControls()
    }

    private fun observeMediaControls() {
        viewModel.exposeMediaState().observe(viewLifecycleOwner) { isPlaying ->
            binding.playPauseBtn.setImageResource(
                if (isPlaying == MediaPlayer.PlayerState.PLAYING) {
                    R.drawable.ic_pause_mini
                } else {
                    R.drawable.ic_play_arrow
                }
            )
        }

        viewModel.exposeCurrentMedia().observe(viewLifecycleOwner) { musicItem ->
            binding.mediaArtist.text = musicItem.artist
            binding.mediaTitle.text = musicItem.title
        }
    }
}