package com.github.zawadz88.audioservice.di

import android.app.Application
import androidx.core.app.ComponentActivity
import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.internal.AudioPlayerServiceManagerImpl
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceConnectionFactory
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceIntentFactory
import dagger.Module
import dagger.Provides

@Suppress("unused")
@Module
object AudioPlayerActivityModule {

    @Provides
    @JvmStatic
    internal fun provideAudioPlayerServiceManager(
        activity: ComponentActivity,
        audioPlayerServiceConnectionFactory: AudioPlayerServiceConnectionFactory,
        audioPlayerServiceIntentFactory: AudioPlayerServiceIntentFactory,
        stateListener: AudioPlayerStateListener
    ): AudioPlayerServiceManager =
        AudioPlayerServiceManagerImpl(activity, audioPlayerServiceConnectionFactory, audioPlayerServiceIntentFactory, stateListener)

    @Provides
    @JvmStatic
    internal fun provideAudioPlayerServiceConnectionFactory(): AudioPlayerServiceConnectionFactory =
        AudioPlayerServiceConnectionFactory()

    @Provides
    @JvmStatic
    internal fun provideAudioPlayerServiceIntentFactory(application: Application): AudioPlayerServiceIntentFactory =
        AudioPlayerServiceIntentFactory(application)
}
