package com.example.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.controller.MediaPlayerController
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.databinding.FragmentMediaListBinding
import com.example.musicplayer.ui.adapter.MediaListAdapter
import com.example.musicplayer.ui.viewmodel.MediaListViewModel

class MediaListFragment: Fragment(R.layout.fragment_media_list) {

    private lateinit var binding: FragmentMediaListBinding
    private val viewModel: MediaListViewModel by viewModels()
    private lateinit var adapter: MediaListAdapter
    private var mediaList: List<MusicItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MediaListAdapter(listener)
        binding.mediaListRv.layoutManager = LinearLayoutManager(context)
        binding.mediaListRv.adapter = adapter
        viewModel.mediaTracks.observe(viewLifecycleOwner, Observer { mediaTracks ->
            mediaList = mediaTracks
            adapter.updateMedia(mediaTracks)
        })
        viewModel.loadMedia()
    }

    private val listener = object : MediaClickListener {
        override fun onMediaItemClick(pos: Int) {
            MediaPlayerController.getInstance().play(mediaList[pos])
            //findNavController().navigate(R.id.musicPlayerFragment)
        }
    }

    interface MediaClickListener {
        fun onMediaItemClick(pos: Int)
    }
}