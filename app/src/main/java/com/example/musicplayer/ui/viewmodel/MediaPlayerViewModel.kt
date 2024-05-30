package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.service.MediaService

class MediaPlayerViewModel : ViewModel() {

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun startMedia(mediaService: MediaService, url: String) {
        mediaService.start(url)
        _isPlaying.value = true
    }

    fun playMedia(mediaService: MediaService, url: String) {
        mediaService.play(url)
        _isPlaying.value = true
    }

    fun pauseMedia(mediaService: MediaService) {
        mediaService.pause()
        _isPlaying.value = false
    }

    fun stopMedia(mediaService: MediaService) {
        mediaService.stop()
        _isPlaying.value = false
    }
}