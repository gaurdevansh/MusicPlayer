package com.example.musicplayer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.media.MediaPlayerImpl

class MediaService : Service() {

    private val binder = MediaBinder()
    private lateinit var player: MediaPlayer
    lateinit var mediaState: LiveData<MediaPlayer.PlayerState>
    lateinit var mediaPosition: LiveData<MediaPlayer.PlayerPosition>
    lateinit var currentMedia: LiveData<MusicItem>

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayerImpl(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun prepare(musicItem: MusicItem) {
        player.prepare(musicItem)
        mediaState = player.state
        currentMedia = player.currentMedia
        mediaPosition = player.currentMediaPosition
    }

    fun playPause() {
        player.playPause()
    }

    inner class MediaBinder : Binder() {
        fun getService(): MediaService = this@MediaService
    }
}