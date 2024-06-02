package com.example.musicplayer.media

import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.data.model.MusicItem

/**
 * Interface for media player.
 */
interface MediaPlayer {

    /**
     * The position of the player.
     */
    data class PlayerPosition(
        /**
         * The current position in milliseconds.
         */
        val current: Long,
        /**
         * The total length in milliseconds.
         */
        val total: Long
    )

    /**
     * Prepare the media player.
     */
    fun prepare(musicItem: MusicItem)

    /**
     * Start playback.
     */
    fun play()

    /**
     * Stop playback.
     */
    fun stop()

    /**
     * Pause playback.
     */
    fun pause()

    /**
     * Toggle playback.
     */
    fun playPause()

    /**
     * Release the media player.
     */
    fun release()

    /**
     * Set the position for playback.
     */
    fun setPosition(pos: Long)

    /**
     * The player state.
     */
    val state: MutableLiveData<PlayerState>

    /**
     * The player position.
     */
    val currentMediaPosition: MutableLiveData<PlayerPosition>

    /**
     * The currently playing Media.
     */
    val currentMedia: MutableLiveData<MusicItem>

    /**
     * The different state of the media player.
     */
    enum class PlayerState {
        INITIAL,
        PREPARED,
        PLAYING,
        PAUSED,
        ENDED,
        STOPPED
    }
}