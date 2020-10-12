package com.example.medyaturkchallenge.ui.main

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Binder
import android.os.IBinder
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.medyaturkchallenge.R
import com.example.medyaturkchallenge.data.remote.services.ApiInterface
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

private const val PLAYBACK_CHANNEL_ID = "channel24"
private const val PLAYBACK_NOTIFICATION_ID = 1

class AudioPlayerService : Service() {

    private var player: SimpleExoPlayer? = null
    private var playerNotificationManager: PlayerNotificationManager? = null
    private val mediaItem: MediaItem = MediaItem.fromUri(ApiInterface.Channel24LiveURL)

    /** Servis ile servise bağlanacak sınıfların
     *arasındaki bağlantıyı sağlayan referans
     */
    private val mBinder = AudioServiceBinder()

    /**
     * Servisin diğer sınıflara paylaşacağı veriler.
     */
    inner class AudioServiceBinder : Binder() {
        val service
            get() = this@AudioPlayerService

        val player
            get() = this@AudioPlayerService.player
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    /**
     * Servisin başlangıç bölümü
     */
    override fun onCreate() {
        super.onCreate()

        // init player
        player = SimpleExoPlayer.Builder(this)
                .build().apply {
                    setMediaItem(mediaItem)
                    playWhenReady = true
                    prepare()
                }

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                applicationContext,
                PLAYBACK_CHANNEL_ID,
                R.string.playback_channel_name,
                R.string.playback_channel_desc,
                PLAYBACK_NOTIFICATION_ID,
                object : PlayerNotificationManager.MediaDescriptionAdapter {
                    override fun getCurrentContentTitle(player: Player): CharSequence {
                        return getString(R.string.def_playback_title)
                    }

                    override fun createCurrentContentIntent(player: Player): PendingIntent? {
                        return PendingIntent.getActivity(
                                applicationContext,
                                0,
                                Intent(applicationContext, MainActivity::class.java),
                                PendingIntent.FLAG_CANCEL_CURRENT
                        )
                    }

                    override fun getCurrentContentText(player: Player): CharSequence? {
                        return null
                    }

                    override fun getCurrentLargeIcon(
                            player: Player,
                            callback: PlayerNotificationManager.BitmapCallback
                    ): Bitmap? {
                        return getBitmapFromVectorDrawable(applicationContext, R.mipmap.ic_launcher)
                    }
                }, object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                stopSelf()
            }

            override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean) {
                if (ongoing) {
                    // Make sure the service will not get destroyed while playing media.
                    startForeground(notificationId, notification)
                } else {
                    // Make notification cancellable.
                    stopForeground(false)
                }
            }
        }
        ).apply {
            //   previous and next actions.
            setUseNavigationActions(true)
            setPlayer(player)
        }
    }

    @MainThread
    private fun getBitmapFromVectorDrawable(
            context: Context,
            @Suppress("SameParameterValue") @DrawableRes drawableId: Int
    ): Bitmap? {
        return ContextCompat.getDrawable(context, drawableId)?.let {
            val drawable = DrawableCompat.wrap(it).mutate()

            val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            bitmap
        }
    }

    @MainThread
    fun changePlayerVolume(view: ImageView) {
        player?.let {
            if (it.volume == 1.0f) {
                it.volume = 0.0f
                view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_volume_off))
            } else {
                it.volume = 1.0f
                view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_volume_up))
            }
        }
    }

    @MainThread
    fun jumpLiveStream() {
        player?.let {
            it.setMediaItem(mediaItem)
            it.playWhenReady = true
        }
    }

    /**
     * Uygulama kapatıldığında tetiklenir.
     */
    override fun onTaskRemoved(rootIntent: Intent?) {// Kullanıcı uygulamayı kapattığında servisi durdur.
        releasePlayer()
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        releasePlayer()
        stopSelf()
        super.onDestroy()
    }

    /**
     * player'ı ve playerNotificationManager'ı sil
     */
    private fun releasePlayer() {
        player?.let {
            it.release()
            player = null
        }
        playerNotificationManager?.let {
            it.setPlayer(null)
            playerNotificationManager = null
        }
    }
}