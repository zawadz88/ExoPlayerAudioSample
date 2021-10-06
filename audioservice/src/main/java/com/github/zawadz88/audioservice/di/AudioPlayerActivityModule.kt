package com.github.zawadz88.audioservice.di

import android.app.Application
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceConnectionFactory
import com.github.zawadz88.audioservice.internal.factory.AudioPlayerServiceIntentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AudioPlayerActivityModule {

    @Provides
    internal fun provideAudioPlayerServiceConnectionFactory(): AudioPlayerServiceConnectionFactory =
        AudioPlayerServiceConnectionFactory()

    @Provides
    internal fun provideAudioPlayerServiceIntentFactory(application: Application): AudioPlayerServiceIntentFactory =
        AudioPlayerServiceIntentFactory(application)
}
