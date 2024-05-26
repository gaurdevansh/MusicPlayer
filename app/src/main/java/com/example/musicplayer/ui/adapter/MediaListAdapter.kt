package com.example.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.model.MediaItem
import com.example.musicplayer.databinding.LayoutMediaItemBinding

class MediaListAdapter: RecyclerView.Adapter<MediaListAdapter.MediaListViewHolder>() {

    private var mediaList: List<MediaItem> = emptyList()

    inner class MediaListViewHolder(private val binding: LayoutMediaItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            binding.mediaTitle.text = mediaItem.title
            binding.mediaArtist.text = mediaItem.artist
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

    fun updateMedia(mediaList: List<MediaItem>) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }
}