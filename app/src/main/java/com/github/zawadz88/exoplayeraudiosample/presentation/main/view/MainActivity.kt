package com.github.zawadz88.exoplayeraudiosample.presentation.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.internal.activity.BaseActivity
import com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel.MainViewModel
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerServiceManager
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerStateListener
import kotlinx.android.synthetic.main.activity_main.nextButton
import kotlinx.android.synthetic.main.activity_main.previousButton
import kotlinx.android.synthetic.main.activity_main.togglePlaybackButton
import javax.inject.Inject

@Suppress("ProtectedInFinal")
class MainActivity : BaseActivity() {

    internal val audioPlayerStateListener = object : AudioPlayerStateListener {

        override fun onPlaybackStateUpdated(playWhenReady: Boolean, hasError: Boolean) = updatePlaybackState(playWhenReady)

        override fun onCurrentWindowUpdated(hasNext: Boolean, hasPrevious: Boolean) = updateCurrentWindow(hasNext = hasNext, hasPrevious = hasPrevious)
    }

    @Inject
    protected lateinit var audioManager: AudioPlayerServiceManager

    private lateinit var viewModel: MainViewModel

    private var currentlyPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        viewModel = ViewModelProvider(this, viewModelFactory).get<MainViewModel>().apply {
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
        val imageResource = if (playWhenReady) R.drawable.exo_controls_pause else R.drawable.exo_controls_play
        togglePlaybackButton.setImageResource(imageResource)
    }
}
