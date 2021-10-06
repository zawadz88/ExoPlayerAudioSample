package com.github.zawadz88.exoplayeraudiosample.presentation.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.di.AudioPlayerServiceManagerProvider
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.internal.activity.BaseActivity
import com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@Suppress("ProtectedInFinal")
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    protected lateinit var audioPlayerServiceManagerProvider: AudioPlayerServiceManagerProvider

    private val audioPlayerStateListener = object : AudioPlayerStateListener {

        override fun onPlaybackStateUpdated(playWhenReady: Boolean, hasError: Boolean) = updatePlaybackState(
            playWhenReady
        )

        override fun onCurrentWindowUpdated(showNextAction: Boolean, showPreviousAction: Boolean) = updateCurrentWindow(
            hasNext = showNextAction,
            hasPrevious = showPreviousAction
        )
    }

    private lateinit var audioManager: AudioPlayerServiceManager

    private lateinit var viewModel: MainViewModel

    private var currentlyPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        audioManager = audioPlayerServiceManagerProvider.create(audioPlayerStateListener)
        initializeViews()
        viewModel = ViewModelProvider(this).get<MainViewModel>().apply {
            loadContent()
        }
    }

    private fun initializeViews() {
        togglePlaybackButton.setOnClickListener { audioManager.changePlayback(!currentlyPlaying) }
        nextButton.setOnClickListener { audioManager.next() }
        previousButton.setOnClickListener { audioManager.previous() }
    }

    private fun updateCurrentWindow(hasNext: Boolean, hasPrevious: Boolean) {
        nextButton.isEnabled = hasNext
        previousButton.isEnabled = hasPrevious
    }

    private fun updatePlaybackState(playWhenReady: Boolean) {
        currentlyPlaying = playWhenReady
        val imageResource = if (playWhenReady) R.drawable.ic_pause_24dp else R.drawable.ic_play_24dp
        togglePlaybackButton.setImageResource(imageResource)
    }
}
