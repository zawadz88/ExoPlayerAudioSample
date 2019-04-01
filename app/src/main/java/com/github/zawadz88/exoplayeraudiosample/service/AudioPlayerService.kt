package com.github.zawadz88.exoplayeraudiosample.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.Samples
import com.github.zawadz88.exoplayeraudiosample.Samples.SAMPLES
import com.github.zawadz88.exoplayeraudiosample.extension.printIntentExtras
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.Player.STATE_IDLE
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback
import com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
import com.google.android.exoplayer2.ui.PlayerNotificationManager.NotificationListener
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import timber.log.Timber

class AudioPlayerService : Service() {

    companion object {
        const val INTENT_KEY_SHOULD_PLAY = "shouldPlay"
        const val INTENT_KEY_NEXT = "next"
        const val INTENT_KEY_PREVIOUS = "previous"

        private const val PLAYBACK_CHANNEL_ID = "playback_channel_id"
        private const val PLAYBACK_NOTIFICATION_ID = 6789
    }

    private var player: SimpleExoPlayer? = null

    private var playerNotificationManager: PlayerNotificationManager? = null

    private var currentlyDisplayedNotification: Notification? = null

    private var playerPrepared: Boolean = false

    private val playerEventListener = object : Player.EventListener {

        override fun onPlayerError(error: ExoPlaybackException) {
            Timber.w(error, "onPlayerError type: ${error.type}")
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Timber.d("onPlayerStateChanged -> playWhenReady: $playWhenReady, playbackState: $playbackState, playbackError: ${player?.playbackError}")

            if (player?.playbackError != null) {
                Timber.d("Error detected - stopping service and notification")
                stopForeground(true)
                clearPlayerNotificationManager()
                playerPrepared = false
                return
            }

            if (shouldPreparePlayerAgain(playWhenReady, playbackState)) {
                Timber.d("Preparing player again")
                preparePlayer()
            }

            when {
                !playWhenReady && currentlyDisplayedNotification != null -> {
                    Timber.d("Stopping foreground Service")
                    stopForeground(false)
                    playerNotificationManager?.setOngoing(false)
                }
                currentlyDisplayedNotification != null -> {
                    Timber.d("Starting foreground Service")
                    startForeground(PLAYBACK_NOTIFICATION_ID, currentlyDisplayedNotification)
                    playerNotificationManager?.setOngoing(true)
                }
            }
        }
    }

    private val notificationListener = object : NotificationListener {

        override fun onNotificationStarted(notificationId: Int, notification: Notification) {
            Timber.i("Notification started: $notification")
            currentlyDisplayedNotification = notification
            startForeground(notificationId, notification)
        }

        override fun onNotificationCancelled(notificationId: Int) {
            Timber.i("Notification cancelled")
            currentlyDisplayedNotification = null
            clearPlayerNotificationManager()
            stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("Created")

        this.player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector()).apply {
            addListener(playerEventListener)
        }
    }

    override fun onDestroy() {
        Timber.i("Destroyed")
        clearPlayerNotificationManager()
        player?.run {
            removeListener(playerEventListener)
            release()
        }
        player = null
        currentlyDisplayedNotification = null

        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = LocalBinder()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.i("Starting command with Intent: $intent")
        intent.printIntentExtras()

        when {
            intent.hasExtra(INTENT_KEY_SHOULD_PLAY) -> {
                val shouldPlay = intent.getBooleanExtra(INTENT_KEY_SHOULD_PLAY, true)
                player!!.playWhenReady = shouldPlay

                if (!playerPrepared) preparePlayer()
                playerNotificationManager ?: preparePlayerNotificationManager()
            }
            intent.getBooleanExtra(INTENT_KEY_NEXT, false) -> goToNext()
            intent.getBooleanExtra(INTENT_KEY_PREVIOUS, false) -> goToPreviousOrBeginning()
        }

        // https://github.com/google/ExoPlayer/issues/4256
        return Service.START_NOT_STICKY
    }

    private fun goToPreviousOrBeginning() {
        player?.run {
            if (hasPrevious()) {
                previous()
            } else {
                seekToDefaultPosition()
            }
        }
    }

    private fun goToNext() {
        player?.next()
    }

    private fun preparePlayerNotificationManager() {
        Timber.d("Preparing PlayerNotificationManager")
        val context: Context = this
        playerNotificationManager = createWithNotificationChannel(
            context,
            PLAYBACK_CHANNEL_ID,
            R.string.channel_name,
            PLAYBACK_NOTIFICATION_ID,
            createMediaDescriptionAdapter(context)
        ).apply {
            setNotificationListener(notificationListener)
            setPlayer(player)
            setRewindIncrementMs(0L)
            setFastForwardIncrementMs(0L)
        }
    }

    private fun createMediaDescriptionAdapter(context: Context): MediaDescriptionAdapter = object : MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): String = SAMPLES[player.currentWindowIndex].title

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        override fun getCurrentContentText(player: Player): String? = SAMPLES[player.currentWindowIndex].description

        override fun getCurrentLargeIcon(player: Player, callback: BitmapCallback): Bitmap? = Samples.getBitmap(context, SAMPLES[player.currentWindowIndex].bitmapResource)
    }

    private fun clearPlayerNotificationManager() {
        playerNotificationManager?.setPlayer(null)
        playerNotificationManager = null
    }

    private fun shouldPreparePlayerAgain(playWhenReady: Boolean, playbackState: Int) = playWhenReady && (playbackState == STATE_IDLE || playbackState == STATE_ENDED)

    private fun preparePlayer() {
        playerPrepared = true
        player?.prepare(createMediaSource())
    }

    private fun createMediaSource(): ConcatenatingMediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))
        val concatenatingMediaSource = ConcatenatingMediaSource()
        // TODO: load data from some real-life source
        for (sample in SAMPLES) {
            val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(sample.uri)
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    inner class LocalBinder : Binder() {

        val boundPlayer: ExoPlayer
            get() = checkNotNull(player) { "Expected player to be not null" }
    }
}
