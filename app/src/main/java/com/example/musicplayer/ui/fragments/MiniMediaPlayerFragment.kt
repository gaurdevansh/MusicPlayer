package com.example.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.example.musicplayer.databinding.MiniMediaPlayerBinding

class MiniMediaPlayerFragment : Fragment(R.layout.mini_media_player) {

    private lateinit var binding: MiniMediaPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  MiniMediaPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }
}