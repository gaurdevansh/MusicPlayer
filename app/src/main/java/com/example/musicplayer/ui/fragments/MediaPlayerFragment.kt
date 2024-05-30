package com.example.musicplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding
import com.example.musicplayer.service.MediaService
import com.example.musicplayer.ui.MainActivity
import com.example.musicplayer.ui.viewmodel.MediaPlayerViewModel

class MediaPlayerFragment: Fragment(R.layout.fragment_music_player) {

    private lateinit var binding: FragmentMusicPlayerBinding

    private val viewModel: MediaPlayerViewModel by activityViewModels()
    private var mediaService: MediaService? = null
    private var isPlaying = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mediaService = (activity as MainActivity).getMediaService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playPauseBtn.setOnClickListener {
            val url = "/storage/emulated/0/Download/410.mp3"
            if (!isPlaying) {
                mediaService?.let { service ->
                    viewModel.playMedia(service, url)
                }
            } else {
                mediaService?.let { service ->
                    viewModel.pauseMedia(service)
                }
            }
            isPlaying = !isPlaying
        }

        viewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
            binding.playPauseBtn.setImageResource(
                if (isPlaying) {
                    R.drawable.ic_pause
                } else {
                    R.drawable.ic_play
                }
            )
        }
    }
}