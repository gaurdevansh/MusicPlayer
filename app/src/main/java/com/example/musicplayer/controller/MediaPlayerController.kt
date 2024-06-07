package com.example.musicplayer.controller

import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.service.MediaService

class MediaPlayerController {

    companion object {
        @Volatile
        private var instance: MediaPlayerController? = null

        fun getInstance(): MediaPlayerController =
            instance ?: synchronized(this) {
                instance ?: MediaPlayerController().also { instance = it }
            }
    }

    private var currentPlayingMedia: MusicItem? = null
    private lateinit var mediaService: MediaService

    fun setCurrentMedia(musicItem: MusicItem) {
        this.currentPlayingMedia = musicItem
    }

    fun getCurrentMedia() = this.currentPlayingMedia

    fun setMediaService(mediaService: MediaService) {
        this.mediaService = mediaService
    }

}