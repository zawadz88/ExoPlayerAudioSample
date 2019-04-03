package com.github.zawadz88.audioservice.internal.factory

import android.app.Application
import android.content.Intent
import com.github.zawadz88.audioservice.internal.AudioPlayerService

internal class AudioPlayerServiceIntentFactory(private val application: Application) {

    internal fun createBaseIntent(): Intent = Intent(application, AudioPlayerService::class.java)

    internal fun createPlayNextIntent(): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_NEXT, true)
    }

    internal fun createPlayPreviousIntent(): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_PREVIOUS, true)
    }

    internal fun createChangePlaybackIntent(shouldPlay: Boolean): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_SHOULD_PLAY, shouldPlay)
    }
}
