package com.example.musicplayer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayer.R
import com.example.musicplayer.controller.MediaPlayerController
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.service.MediaService
import com.example.musicplayer.ui.fragments.HomeFragment


private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var mediaService: MediaService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as MediaService.MediaBinder
            mediaService = binder.getService()
            isBound = true
            MediaPlayerController.getInstance().setMediaService(mediaService!!)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mediaService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        MediaPlayerController.getInstance().setContext(this)
        MediaPlayerController.getInstance().setFragmentManager(supportFragmentManager)
        checkPermissions()

        /*onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }

        })*/
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MediaService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun getMediaService(): MediaService? {
        return mediaService
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        } else {
            //loadMediaItems()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //loadMediaItems()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showMediaPlayer() {
        supportFragmentManager.commit {
            replace(R.id.full_media_player_container, HomeFragment::class.java, null)
                .addToBackStack("FullMediaPlayer")
        }
    }

    /*override fun onBackPressed() {
        when (MediaPlayerController.getInstance().screenState) {
            MediaPlayerController.MediaPlayerScreenState.NONE -> {
                super.onBackPressed()
            }
            MediaPlayerController.MediaPlayerScreenState.MINI -> {
                MediaPlayerController.getInstance().removeMediaPlayerScreen()
            }
            MediaPlayerController.MediaPlayerScreenState.FULL -> {
                MediaPlayerController.getInstance().removeMediaPlayerScreen()
            }
        }
    }*/

    override fun onBackPressed() {
        if (MediaPlayerController.getInstance().screenState ==
            MediaPlayerController.MediaPlayerScreenState.MINI &&
            binding.bottomNavigation.selectedItemId == R.id.homeFragment) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    fun setBottomNavMenuVisibility(isVisible: Boolean) {
        binding.bottomNavigation.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}