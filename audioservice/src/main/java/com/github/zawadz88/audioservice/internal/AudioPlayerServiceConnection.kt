package com.github.zawadz88.audioservice.internal

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import timber.log.Timber

internal class AudioPlayerServiceConnection(private val stateListener: AudioPlayerStateListener) : ServiceConnection {

    private var serviceBinder: AudioPlayerService.LocalBinder? = null

    private val playerEventListener = object : Player.EventListener {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Timber.i("onPlayerStateChanged -> playWhenReady: $playWhenReady, playbackState: $playbackState")
            stateListener.onPlaybackStateUpdated(playWhenReady, serviceBinder?.boundPlayer?.playbackError != null)
            serviceBinder?.boundPlayer?.let { updateCurrentWindowCallbackWithPlayer(it) }
        }

        override fun onPositionDiscontinuity(reason: Int) {
            Timber.i("onPositionDiscontinuity -> reason: $reason")
            serviceBinder?.boundPlayer?.let {
                updateCurrentWindowCallbackWithPlayer(it)
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Timber.i("Disconnected: $name")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        if (service !is AudioPlayerService.LocalBinder) return

        Timber.i("Connected: $name")
        serviceBinder = service
        val boundPlayer = service.boundPlayer
        stateListener.onPlaybackStateUpdated(boundPlayer.playWhenReady, serviceBinder?.boundPlayer?.playbackError != null)
        updateCurrentWindowCallbackWithPlayer(boundPlayer)
        boundPlayer.addListener(playerEventListener)
    }

    /**
     * Must be called when we call [android.app.Activity.unbindService].
     */
    internal fun onUnbind() {
        serviceBinder?.boundPlayer?.removeListener(playerEventListener)
    }

    private fun updateCurrentWindowCallbackWithPlayer(boundPlayer: ExoPlayer) {
        stateListener.onCurrentWindowUpdated(showNextAction = boundPlayer.hasNext(), showPreviousAction = boundPlayer.shouldShowPrevious())
    }

    private fun ExoPlayer.shouldShowPrevious() =
        this.hasPrevious() || this.playbackState == Player.STATE_READY
}
