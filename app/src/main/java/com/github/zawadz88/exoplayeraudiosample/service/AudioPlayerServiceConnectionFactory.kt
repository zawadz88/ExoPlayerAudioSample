package com.github.zawadz88.exoplayeraudiosample.service

import javax.inject.Inject

class AudioPlayerServiceConnectionFactory
@Inject constructor() {

    fun createConnection(stateListener: AudioPlayerStateListener): AudioPlayerServiceConnection = AudioPlayerServiceConnection(stateListener)
}
