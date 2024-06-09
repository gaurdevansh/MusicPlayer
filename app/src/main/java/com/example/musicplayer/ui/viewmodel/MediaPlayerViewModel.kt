package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.service.MediaService

class MediaPlayerViewModel : ViewModel() {

    fun exposeMediaState(): LiveData<MediaPlayer.PlayerState> {
        return MediaService.mediaState
    }

    fun exposeCurrentMedia(): LiveData<MusicItem> {
        return MediaService.currentMedia
    }

    fun exposeMediaPosition(): LiveData<MediaPlayer.PlayerPosition> {
        return MediaService.mediaPosition
    }

    fun prepare(mediaService: MediaService, musicItem: MusicItem) {
        mediaService.prepare(musicItem)
    }

    fun playPause(mediaService: MediaService) {
        mediaService.playPause()
    }

    fun setMediaPosition(mediaService: MediaService, pos: Long) {
        mediaService.setMediaPosition(pos)
    }
}