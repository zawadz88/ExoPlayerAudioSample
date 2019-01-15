package com.github.zawadz88.exoplayeraudiosample.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.google.android.exoplayer2.Player
import timber.log.Timber

class AudioPlayerServiceConnection(
    private val updatePlaybackStateCallback: (playWhenReady: Boolean) -> Unit
) : ServiceConnection {

    private var audioService: AudioPlayerService.LocalBinder? = null

    private val playerEventListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Timber.i("onPlayerStateChanged -> playWhenReady: $playWhenReady, playbackState: $playbackState")
            updatePlaybackStateCallback(playWhenReady)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Timber.i("Disconnected: $name")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        if (service !is AudioPlayerService.LocalBinder) return

        audioService = service
        Timber.i("Connected: $name")
        updatePlaybackStateCallback(service.boundPlayer.playWhenReady)
        service.boundPlayer.addListener(playerEventListener)
    }

    /**
     * Must be called when we call [android.app.Activity.unbindService].
     */
    fun onUnbind() {
        audioService?.boundPlayer?.removeListener(playerEventListener)
    }
}
