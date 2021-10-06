package com.github.zawadz88.audioservice.internal

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceConnectionFactory
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceIntentFactory
import com.google.android.exoplayer2.util.Util
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

internal class AudioPlayerServiceManagerImpl @AssistedInject constructor(
    private val activity: FragmentActivity,
    private val audioPlayerServiceConnectionFactory: AudioPlayerServiceConnectionFactory,
    private val audioPlayerServiceIntentFactory: AudioPlayerServiceIntentFactory,
    @Assisted stateListener: AudioPlayerStateListener
) : AudioPlayerServiceManager {

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

    override fun changePlayback(shouldPlay: Boolean) {
        val intent = audioPlayerServiceIntentFactory.createChangePlaybackIntent(shouldPlay)
        Util.startForegroundService(activity, intent)
    }

    override fun next() {
        val intent = audioPlayerServiceIntentFactory.createPlayNextIntent()
        Util.startForegroundService(activity, intent)
    }

    override fun previous() {
        val intent = audioPlayerServiceIntentFactory.createPlayPreviousIntent()
        Util.startForegroundService(activity, intent)
    }

    private fun connect(stateListener: AudioPlayerStateListener) {
        Timber.d("Connecting to AudioPlayerServiceConnection")
        serviceConnection = audioPlayerServiceConnectionFactory.createConnection(stateListener).also {
            val intent = audioPlayerServiceIntentFactory.createBaseIntent()
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
