package com.example.musicplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.R
import com.example.musicplayer.controller.MediaPlayerController
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.service.MediaService
import com.example.musicplayer.ui.MainActivity
import com.example.musicplayer.ui.viewmodel.MediaPlayerViewModel
import com.example.musicplayer.util.TimeUtils

class MediaPlayerFragment : Fragment(R.layout.fragment_music_player) {

    private lateinit var binding: FragmentMusicPlayerBinding

    private val viewModel: MediaPlayerViewModel by activityViewModels()
    private var mediaService: MediaService? = null
    private lateinit var callback: OnBackPressedCallback

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

        (activity as MainActivity).setBottomNavMenuVisibility(false)
        binding.playPauseBtn.setOnClickListener {
            mediaService?.let { service ->
                viewModel.playPause(service)
            }
        }

        observeMediaControls()

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MediaPlayerController.getInstance().removeMediaPlayerScreen()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun observeMediaControls() {
        mediaService?.let {
            viewModel.exposeMediaState().observe(viewLifecycleOwner) { isPlaying ->
                binding.playPauseBtn.setImageResource(
                    if (isPlaying == MediaPlayer.PlayerState.PLAYING) {
                        R.drawable.ic_pause
                    } else {
                        R.drawable.ic_play
                    }
                )
            }

            viewModel.exposeCurrentMedia().observe(viewLifecycleOwner) { musicItem ->
                binding.mediaArtist.text = musicItem.artist
                binding.mediaTitle.text = musicItem.title
            }

            viewModel.exposeMediaPosition().observe(viewLifecycleOwner) { (current, total) ->
                binding.currentDuration.text = TimeUtils.getFormattedTime(current)
                binding.totalDuration.text = TimeUtils.getFormattedTime(total)
                binding.seekbar.max = (total / 1000).toInt()
                binding.seekbar.progress = (current / 1000).toInt()
            }

            binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    //NOP
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                    //NOP
                }
                override fun onStopTrackingTouch(seekbar: SeekBar?) {
                    viewModel.setMediaPosition(it, seekbar?.progress!!.toLong() * 1000L)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setBottomNavMenuVisibility(true)
        callback.remove()
    }
}