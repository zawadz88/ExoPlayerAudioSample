package com.github.zawadz88.audioservice.di

import com.github.zawadz88.audioservice.internal.AudioPlayerService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class AudioPlayerServiceModule {

    @ContributesAndroidInjector
    internal abstract fun contributeAudioPlayerService(): AudioPlayerService
}
