package com.github.zawadz88.exoplayeraudiosample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerService
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.nextButton
import kotlinx.android.synthetic.main.activity_main.previousButton
import kotlinx.android.synthetic.main.activity_main.togglePlaybackButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.i("Audio Service disconnected: $name")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service is AudioPlayerService.LocalBinder) {
                updatePlaybackState(service.boundPlayer.playWhenReady)
                service.boundPlayer.addListener(object : Player.EventListener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        Timber.i("Activity onPlayerStateChanged -> playWhenReady: $playWhenReady, playbackState: $playbackState")
                        updatePlaybackState(playWhenReady)
                    }
                })
            }
        }
    }

    private var currentlyPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startPlayback()
        initializeViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun initializeViews() {
        togglePlaybackButton.setOnClickListener {
            val intent = Intent(this, AudioPlayerService::class.java).apply {
                putExtra("shouldPlay", !currentlyPlaying)
            }
            startService(intent)
        }
        nextButton.setOnClickListener { Timber.i("Next clicked") }
        previousButton.setOnClickListener { Timber.i("Previous clicked") }
    }

    private fun startPlayback() {
        val intent = Intent(this, AudioPlayerService::class.java)
        Util.startForegroundService(this, intent)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun updatePlaybackState(playWhenReady: Boolean) {
        currentlyPlaying = playWhenReady
        val imageResource = if (playWhenReady) R.drawable.exo_controls_pause else R.drawable.exo_controls_play
        togglePlaybackButton.setImageResource(imageResource)
    }
}
