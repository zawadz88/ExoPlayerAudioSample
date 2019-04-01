package com.github.zawadz88.exoplayeraudiosample.service

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil

class PlayerNotificationManagerWithHiddenStopAction(
    context: Context?,
    channelId: String?,
    notificationId: Int,
    mediaDescriptionAdapter: MediaDescriptionAdapter?,
    customActionReceiver: CustomActionReceiver? = null
) : PlayerNotificationManager(context, channelId, notificationId, mediaDescriptionAdapter, customActionReceiver) {

    override fun getActions(player: Player?): MutableList<String> =
        super.getActions(player).filter { it != PlayerNotificationManager.ACTION_STOP }.toMutableList()
}

/**
 * @see PlayerNotificationManager
 */
fun createWithNotificationChannel(
    context: Context,
    channelId: String,
    @StringRes channelName: Int,
    notificationId: Int,
    mediaDescriptionAdapter: PlayerNotificationManager.MediaDescriptionAdapter
): PlayerNotificationManagerWithHiddenStopAction {
    NotificationUtil.createNotificationChannel(context, channelId, channelName, NotificationUtil.IMPORTANCE_LOW)
    return PlayerNotificationManagerWithHiddenStopAction(context, channelId, notificationId, mediaDescriptionAdapter)
}
