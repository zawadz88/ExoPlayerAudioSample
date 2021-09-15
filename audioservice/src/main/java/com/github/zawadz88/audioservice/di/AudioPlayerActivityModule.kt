package com.github.zawadz88.audioservice.di

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.internal.AudioPlayerServiceManagerImpl
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceConnectionFactory
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceIntentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Suppress("unused")
@Module
@InstallIn(ActivityComponent::class)
object AudioPlayerActivityModule {

    @Provides
    internal fun provideAudioPlayerServiceManager(
        activity: FragmentActivity,
        audioPlayerServiceConnectionFactory: AudioPlayerServiceConnectionFactory,
        audioPlayerServiceIntentFactory: AudioPlayerServiceIntentFactory,
        stateListener: AudioPlayerStateListener
    ): AudioPlayerServiceManager =
        AudioPlayerServiceManagerImpl(activity, audioPlayerServiceConnectionFactory, audioPlayerServiceIntentFactory, stateListener)

    @Provides
    internal fun provideAudioPlayerServiceConnectionFactory(): AudioPlayerServiceConnectionFactory =
        AudioPlayerServiceConnectionFactory()

    @Provides
    internal fun provideAudioPlayerServiceIntentFactory(application: Application): AudioPlayerServiceIntentFactory =
        AudioPlayerServiceIntentFactory(application)
}
