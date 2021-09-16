package com.github.zawadz88.exoplayeraudiosample.presentation.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.github.zawadz88.audioservice.AudioPlayerServiceManager
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.di.AudioPlayerActivityComponent
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.internal.activity.BaseActivity
import com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel.MainViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

@Suppress("ProtectedInFinal")
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @InstallIn(AudioPlayerActivityComponent::class)
    @EntryPoint
    interface MainActivityEntryPoint {

        fun audioPlayerServiceManager(): AudioPlayerServiceManager
    }

    @Inject
    protected lateinit var componentBuilderProvider: Provider<AudioPlayerActivityComponent.Builder>

    private val audioPlayerStateListener = object : AudioPlayerStateListener {

        override fun onPlaybackStateUpdated(playWhenReady: Boolean, hasError: Boolean) = updatePlaybackState(
            playWhenReady
        )

        override fun onCurrentWindowUpdated(showNextAction: Boolean, showPreviousAction: Boolean) = updateCurrentWindow(
            hasNext = showNextAction,
            hasPrevious = showPreviousAction
        )
    }

    private val audioManager: AudioPlayerServiceManager by lazy {
        EntryPoints.get(
            componentBuilderProvider.get().stateListener(audioPlayerStateListener).build(),
            MainActivityEntryPoint::class.java
        ).audioPlayerServiceManager()
    }

    private lateinit var viewModel: MainViewModel

    private var currentlyPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
