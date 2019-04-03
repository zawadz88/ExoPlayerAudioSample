package com.github.zawadz88.audioservice

interface AudioPlayerServiceManager {

    fun changePlayback(shouldPlay: Boolean)

    fun next()

    fun previous()
}
