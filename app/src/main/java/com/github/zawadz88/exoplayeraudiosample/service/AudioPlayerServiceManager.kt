package com.github.zawadz88.exoplayeraudiosample.service

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.util.Util
import timber.log.Timber

class AudioPlayerServiceManager(
    private val activity: AppCompatActivity,
    private val audioPlayerServiceConnectionFactory: AudioPlayerServiceConnectionFactory,
    private val audioPlayerServiceIntentFactory: AudioPlayerServiceIntentFactory,
    stateListener: AudioPlayerStateListener
) {

    init {
        @Suppress("unused")
        activity.lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                connect(stateListener)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                disconnect()
            }
        })
    }

    private var serviceConnection: AudioPlayerServiceConnection? = null

    fun changePlayback(shouldPlay: Boolean) {
        val intent = audioPlayerServiceIntentFactory.createChangePlaybackIntent(shouldPlay)
        activity.startService(intent)
    }

    fun next() {
        val intent = audioPlayerServiceIntentFactory.createPlayNextIntent()
        activity.startService(intent)
    }

    fun previous() {
        val intent = audioPlayerServiceIntentFactory.createPlayPreviousIntent()
        activity.startService(intent)
    }

    private fun connect(stateListener: AudioPlayerStateListener) {
        Timber.d("Connecting to AudioPlayerServiceConnection")
        serviceConnection = audioPlayerServiceConnectionFactory.createConnection(stateListener).also {
            val intent = audioPlayerServiceIntentFactory.createBaseIntent()
            Util.startForegroundService(activity, intent)
            activity.bindService(intent, it, Context.BIND_AUTO_CREATE)
        }
    }

    private fun disconnect() {
        serviceConnection?.let {
            Timber.d("Disconnecting from AudioPlayerServiceConnection")
            it.onUnbind()
            activity.unbindService(it)
            serviceConnection = null
            return
        }

        Timber.w("Service was not connected when trying to disconnect!")
    }
}
