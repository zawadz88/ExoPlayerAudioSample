package com.github.zawadz88.exoplayeraudiosample.service

interface AudioPlayerStateListener {

    fun onPlaybackStateUpdated(playWhenReady: Boolean) {}

    fun onCurrentWindowUpdated(hasNext: Boolean) {}
}
