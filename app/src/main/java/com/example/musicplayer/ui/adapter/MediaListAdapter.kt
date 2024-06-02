package com.example.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.databinding.LayoutMediaItemBinding

class MediaListAdapter: RecyclerView.Adapter<MediaListAdapter.MediaListViewHolder>() {

    private var mediaList: List<MusicItem> = emptyList()

    inner class MediaListViewHolder(private val binding: LayoutMediaItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(musicItem: MusicItem) {
            binding.mediaTitle.text = musicItem.title
            binding.mediaArtist.text = musicItem.artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        val binding = LayoutMediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaListViewHolder(binding)
    }

    override fun getItemCount() = mediaList.size

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    fun updateMedia(mediaList: List<MusicItem>) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }
}