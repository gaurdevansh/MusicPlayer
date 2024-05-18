package com.example.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicPlayerBinding

class MusicPlayerFragment: Fragment(R.layout.fragment_music_player) {

    private lateinit var binding: FragmentMusicPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }
}