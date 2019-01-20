package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerServiceConnectionFactory
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerServiceIntentFactory
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerServiceManager
import dagger.Module
import dagger.Provides

@Module
object MainActivityModule {

    @Provides
    @JvmStatic
    fun provideAudioPlayerServiceManager(
        activity: MainActivity,
        audioPlayerServiceConnectionFactory: AudioPlayerServiceConnectionFactory,
        audioPlayerServiceIntentFactory: AudioPlayerServiceIntentFactory
    ): AudioPlayerServiceManager =
        AudioPlayerServiceManager(activity, audioPlayerServiceConnectionFactory, audioPlayerServiceIntentFactory, activity.audioPlayerStateListener)
}
