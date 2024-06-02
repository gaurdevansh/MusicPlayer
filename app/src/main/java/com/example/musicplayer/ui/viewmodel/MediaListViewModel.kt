package com.example.musicplayer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.data.repository.MediaRepository

class MediaListViewModel(application: Application): AndroidViewModel(application) {

    private val repository = MediaRepository(application)
    private val _mediaTracks = MutableLiveData<List<MusicItem>>()
    val mediaTracks: LiveData<List<MusicItem>> get() = _mediaTracks

    fun loadMedia() {
        _mediaTracks.value = repository.loadMedia()
    }

}