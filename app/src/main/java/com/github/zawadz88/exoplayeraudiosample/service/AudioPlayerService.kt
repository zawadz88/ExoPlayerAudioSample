package com.github.zawadz88.exoplayeraudiosample.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.github.zawadz88.exoplayeraudiosample.MainActivity
import com.github.zawadz88.exoplayeraudiosample.R
import com.github.zawadz88.exoplayeraudiosample.Samples
import com.github.zawadz88.exoplayeraudiosample.Samples.SAMPLES
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
        private const val PLAYBACK_CHANNEL_ID = "test_id"
        private const val PLAYBACK_NOTIFICATION_ID = 6789
    }

    private var player: SimpleExoPlayer? = null

    private var playerNotificationManager: PlayerNotificationManager? = null

    private var currentlyDisplayedNotification: Notification? = null

    private val playerEventListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Timber.i("onPlayerStateChanged -> playWhenReady: $playWhenReady, playbackState: $playbackState")
            if (shouldPreparePlayerAgain(playWhenReady, playbackState)) {
                preparePlayerAndPlay()
            }

            when {
                !playWhenReady -> {
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

    override fun onCreate() {
        super.onCreate()
        Timber.i("Audio Service created")

        val player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        this.player = player
        preparePlayerAndPlay()
        player.addListener(playerEventListener)
    }

    override fun onDestroy() {
        Timber.i("Audio Service destroyed")
        clearPlayerNotificationManager()
        player?.release()
        player = null
        currentlyDisplayedNotification = null

        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = LocalBinder()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.i("Audio Service starting command with Intent: $intent")

        if (intent.hasExtra("shouldPlay")) {
            val shouldPlay = intent.getBooleanExtra("shouldPlay", true)
            player!!.playWhenReady = shouldPlay
        }

        playerNotificationManager ?: preparePlayerNotificationManager()

        // https://github.com/google/ExoPlayer/issues/4256
        return Service.START_STICKY
    }

    private fun preparePlayerNotificationManager() {
        val context: Context = this
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
            context,
            PLAYBACK_CHANNEL_ID,
            R.string.channel_name,
            PLAYBACK_NOTIFICATION_ID,
            object : MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): String = SAMPLES[player.currentWindowIndex].title

                override fun createCurrentContentIntent(player: Player): PendingIntent? =
                    PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

                override fun getCurrentContentText(player: Player): String? = SAMPLES[player.currentWindowIndex].description

                override fun getCurrentLargeIcon(player: Player, callback: BitmapCallback): Bitmap? = Samples.getBitmap(context, SAMPLES[player.currentWindowIndex].bitmapResource)
            }
        )
        playerNotificationManager!!.setNotificationListener(object : NotificationListener {

            override fun onNotificationStarted(notificationId: Int, notification: Notification) {
                Timber.i("Notification started: $notification")
                currentlyDisplayedNotification = notification
                startForeground(notificationId, notification)
            }

            override fun onNotificationCancelled(notificationId: Int) {
                Timber.i("Notification cancelled")
                stopSelf()
                clearPlayerNotificationManager()
            }
        })
        playerNotificationManager!!.setPlayer(player)
    }

    private fun clearPlayerNotificationManager() {
        playerNotificationManager?.setPlayer(null)
        playerNotificationManager = null
    }

    private fun shouldPreparePlayerAgain(playWhenReady: Boolean, playbackState: Int) = playWhenReady && (playbackState == STATE_IDLE || playbackState == STATE_ENDED)

    private fun preparePlayerAndPlay() {
        player?.run {
            prepare(createMediaSource())
            playWhenReady = true
        }
    }

    private fun createMediaSource(): ConcatenatingMediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))
        val concatenatingMediaSource = ConcatenatingMediaSource()
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
