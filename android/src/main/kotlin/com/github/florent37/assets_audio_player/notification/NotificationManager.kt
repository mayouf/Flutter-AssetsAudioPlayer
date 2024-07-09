package com.github.florent37.assets_audio_player.notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.florent37.assets_audio_player.AssetsAudioPlayerPlugin
import java.lang.ref.WeakReference

class NotificationManager(context: Context) {
    private val contextRef = WeakReference(context)
    @Volatile
    private var closed = false

    fun showNotification(playerId: String, audioMetas: AudioMetas, isPlaying: Boolean, notificationSettings: NotificationSettings, stop: Boolean, durationMs: Long) {
        if (closed) return

        val context = contextRef.get() ?: return

        try {
            if (stop) {
                stopNotification()
            } else {
                val intent = Intent(context, NotificationService::class.java).apply {
                    putExtra(NotificationService.EXTRA_NOTIFICATION_ACTION, NotificationAction.Show(
                        isPlaying = isPlaying,
                        audioMetas = audioMetas,
                        playerId = playerId,
                        notificationSettings = notificationSettings,
                        durationMs = durationMs
                    ))
                }
                startForegroundService(context, intent)
            }
            AssetsAudioPlayerPlugin.instance?.assetsAudioPlayer?.registerLastPlayerWithNotif(playerId)
        } catch (t: Throwable) {
            Log.e("NotificationManager", "Error showing notification", t)
        }
    }

    fun stopNotification() {
        val context = contextRef.get() ?: return

        try {
            val intent = Intent(context, NotificationService::class.java).apply {
                putExtra(NotificationService.EXTRA_NOTIFICATION_ACTION, NotificationAction.Hide())
            }
            startForegroundService(context, intent)
        } catch (t: Throwable) {
            Log.e("NotificationManager", "Error stopping notification", t)
        }
    }

    fun hideNotificationService(definitively: Boolean = false) {
        val context = contextRef.get() ?: return

        try {
            context.stopService(Intent(context, NotificationService::class.java))
            closed = definitively
        } catch (t: Throwable) {
            Log.e("NotificationManager", "Error hiding notification service", t)
        }
    }

    private fun startForegroundService(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.startService(intent)
        }
    }
}
