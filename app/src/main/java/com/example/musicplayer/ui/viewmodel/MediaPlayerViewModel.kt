package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.service.MediaService

class MediaPlayerViewModel : ViewModel() {

    fun exposeMediaState(mediaService: MediaService): LiveData<MediaPlayer.PlayerState> {
        return mediaService.mediaState
    }

    fun exposeCurrentMedia(mediaService: MediaService): LiveData<MusicItem> {
        return mediaService.currentMedia
    }

    fun exposeMediaPosition(mediaService: MediaService): LiveData<MediaPlayer.PlayerPosition> {
        return mediaService.mediaPosition
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