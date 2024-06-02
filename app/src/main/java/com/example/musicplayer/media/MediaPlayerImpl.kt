package com.example.musicplayer.media

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.data.model.MusicItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class MediaPlayerImpl(
    private val context: Context
) : MediaPlayer {

    override val state: MutableLiveData<MediaPlayer.PlayerState> =
        MutableLiveData(MediaPlayer.PlayerState.INITIAL)
    override val currentMediaPosition: MutableLiveData<MediaPlayer.PlayerPosition> =
        MutableLiveData(MediaPlayer.PlayerPosition(0L, 0L))
    override val currentMedia: MutableLiveData<MusicItem> = MutableLiveData()

    private val handler = Handler(Looper.getMainLooper())
    private val updatePositionRunnable = object : Runnable {
        override fun run() {
            player.let {
                if (it.isPlaying) {
                    currentMediaPosition.postValue(MediaPlayer.PlayerPosition(
                        it.currentPosition,
                        it.duration)
                    )
                }
                handler.postDelayed(this, 1000L)
            }
        }
    }

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when(playbackState) {
                Player.STATE_ENDED -> {
                    state.postValue(MediaPlayer.PlayerState.ENDED)
                    handler.removeCallbacks(updatePositionRunnable)
                }
            }
        }
    }

    private var player: ExoPlayer

    init {
        player = createPlayer().apply {
            addListener(playerListener)
        }
    }

    private fun createPlayer(): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    override fun prepare(musicItem: MusicItem) {
        val mediaItem = MediaItem.fromUri(musicItem.path)
        player.setMediaItem(mediaItem)
        player.prepare()
        currentMedia.postValue(musicItem)
        state.postValue(MediaPlayer.PlayerState.PREPARED)
    }

    override fun play() {
        player.play()
        updatePlayerPosition()
        state.postValue(MediaPlayer.PlayerState.PLAYING)
    }

    override fun stop() {
        player.stop()
        handler.removeCallbacks(updatePositionRunnable)
        state.postValue(MediaPlayer.PlayerState.STOPPED)
    }

    override fun pause() {
        player.pause()
        handler.removeCallbacks(updatePositionRunnable)
        state.postValue(MediaPlayer.PlayerState.PAUSED)
    }

    override fun playPause() {
        if (state.value == MediaPlayer.PlayerState.PLAYING) {
            pause()
        } else {
            play()
        }
    }

    override fun release() {
        player.release()
        handler.removeCallbacks(updatePositionRunnable)
        state.postValue(MediaPlayer.PlayerState.ENDED)
    }

    override fun setPosition(pos: Long) {
        player.seekTo(pos)
    }

    private fun updatePlayerPosition() {
        handler.post(updatePositionRunnable)
    }
}