package com.github.zawadz88.exoplayeraudiosample.service

interface AudioPlayerStateListener {

    fun onPlaybackStateUpdated(playWhenReady: Boolean, hasError: Boolean) {}

    fun onCurrentWindowUpdated(hasNext: Boolean, hasPrevious: Boolean) {}
}
