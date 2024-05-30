package com.example.musicplayer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlin.math.exp

class MediaService : Service() {

    private val binder = MediaBinder()
    private var exoplayer: ExoPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        exoplayer = ExoPlayer.Builder(this).build()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayer?.release()
        exoplayer = null
    }

    fun start(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoplayer?.setMediaItem(mediaItem)
        exoplayer?.prepare()
        exoplayer?.play()
    }

    fun play(url: String) {
        if (exoplayer?.playbackState == Player.STATE_IDLE || exoplayer?.playbackState == Player.STATE_ENDED) {
            start(url)
        } else {
            exoplayer?.play()
        }
    }

    fun pause() {
        exoplayer?.pause()
    }

    fun stop() {
        exoplayer?.stop()
    }

    inner class MediaBinder : Binder() {
        fun getService(): MediaService = this@MediaService
    }
}