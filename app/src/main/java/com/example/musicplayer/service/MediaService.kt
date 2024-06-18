package com.example.musicplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.R
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.media.MediaPlayer
import com.example.musicplayer.media.MediaPlayerImpl

class MediaService : Service() {

    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "MEDIA_PLAYBACK_CHANNEL"

    private val binder = MediaBinder()
    private lateinit var player: MediaPlayer

    companion object {
        var mediaState = MutableLiveData<MediaPlayer.PlayerState>()
        var mediaPosition = MutableLiveData<MediaPlayer.PlayerPosition>()
        var currentMedia = MutableLiveData<MusicItem>()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayerImpl(this)
        mediaState = player.state
        currentMedia = player.currentMedia
        mediaPosition = player.currentMediaPosition
        startForegroundService()
    }

    fun prepare(musicItem: MusicItem) {
        player.prepare(musicItem)
        player.play()
    }

    fun playPause() {
        player.playPause()
    }

    fun setMediaPosition(pos: Long) {
        player.setPosition(pos)
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setContentText("Song")
            .setSmallIcon(R.drawable.ic_music_note)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Media Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    inner class MediaBinder : Binder() {
        fun getService(): MediaService = this@MediaService
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}