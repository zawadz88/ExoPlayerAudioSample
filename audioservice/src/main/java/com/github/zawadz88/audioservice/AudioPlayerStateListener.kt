package com.github.zawadz88.audioservice

interface AudioPlayerStateListener {

    fun onPlaybackStateUpdated(playWhenReady: Boolean, hasError: Boolean) {}

    fun onCurrentWindowUpdated(showNextAction: Boolean, showPreviousAction: Boolean) {}
}
