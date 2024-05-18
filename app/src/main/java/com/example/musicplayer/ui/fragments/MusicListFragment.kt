package com.example.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicListBinding

class MusicListFragment: Fragment(R.layout.fragment_music_list) {

    private lateinit var binding: FragmentMusicListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }
}