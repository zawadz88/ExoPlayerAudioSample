package com.github.zawadz88.exoplayeraudiosample.service

import android.app.Application
import android.content.Intent
import javax.inject.Inject

class AudioPlayerServiceIntentFactory
@Inject constructor(
    private val application: Application
) {

    fun createBaseIntent(): Intent = Intent(application, AudioPlayerService::class.java)

    fun createPlayNextIntent(): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_NEXT, true)
    }

    fun createPlayPreviousIntent(): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_PREVIOUS, true)
    }

    fun createChangePlaybackIntent(shouldPlay: Boolean): Intent = createBaseIntent().apply {
        putExtra(AudioPlayerService.INTENT_KEY_SHOULD_PLAY, shouldPlay)
    }

}
