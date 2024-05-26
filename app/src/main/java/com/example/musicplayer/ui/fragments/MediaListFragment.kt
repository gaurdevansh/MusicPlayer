package com.example.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMusicListBinding
import com.example.musicplayer.ui.adapter.MediaListAdapter
import com.example.musicplayer.ui.viewmodel.MediaListViewModel

class MediaListFragment: Fragment(R.layout.fragment_music_list) {

    private lateinit var binding: FragmentMusicListBinding
    private val viewModel: MediaListViewModel by viewModels()
    private lateinit var adapter: MediaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MediaListAdapter()
        binding.mediaListRv.layoutManager = LinearLayoutManager(context)
        binding.mediaListRv.adapter = adapter
        viewModel.mediaTracks.observe(viewLifecycleOwner, Observer { mediaTracks ->
            adapter.updateMedia(mediaTracks)
        })
        viewModel.loadMedia()
    }
}