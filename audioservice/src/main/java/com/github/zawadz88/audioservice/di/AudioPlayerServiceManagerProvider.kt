package com.github.zawadz88.audioservice.di

import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.internal.AudioPlayerServiceManagerImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import javax.inject.Inject

class AudioPlayerServiceManagerProvider @Inject internal constructor(
    private val assistedFactory: AssistedAudioPlayerServiceManagerImplFactory
) {
    fun create(stateListener: AudioPlayerStateListener): AudioPlayerServiceManager =
        assistedFactory.create(stateListener)
}

@AssistedFactory
internal abstract class AssistedAudioPlayerServiceManagerImplFactory {

    internal abstract fun create(@Assisted stateListener: AudioPlayerStateListener): AudioPlayerServiceManagerImpl
}
