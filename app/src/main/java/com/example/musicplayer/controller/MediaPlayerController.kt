package com.example.musicplayer.controller

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore.Images.Media
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.musicplayer.R
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.service.MediaService
import com.example.musicplayer.ui.fragments.HomeFragment
import com.example.musicplayer.ui.fragments.MiniMediaPlayerFragment

class MediaPlayerController {

    private var currentPlayingMedia: MusicItem? = null
    private lateinit var mediaService: MediaService
    private var context: Context? = null
    private var currentMediaPlayerScreenState = MediaPlayerScreenState.NONE
    private var fragmentManager: FragmentManager? = null
    var screenState: MediaPlayerScreenState = MediaPlayerScreenState.NONE
    private var homeFragment = HomeFragment()
    private var miniMediaPlayerFragment = MiniMediaPlayerFragment()

    fun setCurrentMedia(musicItem: MusicItem) {
        this.currentPlayingMedia = musicItem
    }

    fun getCurrentMedia() = this.currentPlayingMedia

    fun setMediaService(mediaService: MediaService) {
        this.mediaService = mediaService
    }

    fun play(musicItem: MusicItem) {
        mediaService.prepare(musicItem)
        //(context as MainActivity).showMediaPlayer()
        showMediaPlayerScreen(setScreenState())
    }

    private fun setScreenState(): MediaPlayerScreenState{
        return when (currentMediaPlayerScreenState) {
            MediaPlayerScreenState.NONE -> MediaPlayerScreenState.FULL
            MediaPlayerScreenState.MINI -> MediaPlayerScreenState.MINI
            MediaPlayerScreenState.FULL -> MediaPlayerScreenState.FULL
        }
    }

    private fun showMediaPlayerScreen(screenType: MediaPlayerScreenState) {
        when(screenType) {
            MediaPlayerScreenState.FULL -> {
                fragmentManager?.commit {
                    replace(R.id.full_media_player_container,  homeFragment)
                        .addToBackStack("FullMediaPlayer")
                }
                currentMediaPlayerScreenState = MediaPlayerScreenState.FULL
                screenState = currentMediaPlayerScreenState
            }
            MediaPlayerScreenState.MINI -> {
                fragmentManager?.commit {
                    replace(R.id.mini_media_player_container, miniMediaPlayerFragment)
                        .addToBackStack("MiniMediaPlayer")
                }
                currentMediaPlayerScreenState = MediaPlayerScreenState.MINI
                screenState = currentMediaPlayerScreenState
            }
            MediaPlayerScreenState.NONE -> {}
        }
    }

    fun removeMediaPlayerScreen() {
        if (currentMediaPlayerScreenState == MediaPlayerScreenState.FULL) {
            fragmentManager?.commit {
                remove(homeFragment)
            }
            screenState = MediaPlayerScreenState.MINI
            showMediaPlayerScreen(MediaPlayerScreenState.MINI)
        } else {
            fragmentManager?.commit {
                remove(miniMediaPlayerFragment)
            }
            screenState = MediaPlayerScreenState.NONE
        }
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: MediaPlayerController? = null

        fun getInstance(): MediaPlayerController =
            instance ?: synchronized(this) {
                instance ?: MediaPlayerController().also { instance = it }
            }
    }

    enum class MediaPlayerScreenState {
        NONE,
        MINI,
        FULL
    }

}