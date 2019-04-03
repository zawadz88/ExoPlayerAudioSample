package com.github.zawadz88.audioservice.internal.factory

import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.internal.AudioPlayerServiceConnection

internal class AudioPlayerServiceConnectionFactory {

    internal fun createConnection(stateListener: AudioPlayerStateListener): AudioPlayerServiceConnection =
        AudioPlayerServiceConnection(stateListener)
}
