package adv.vadym.com.tfexercise

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat


object MessageNotification {

    private val NOTIFICATION_TAG = "Message"
    var switcher = false
    var languageId = 0

    fun notify(context: Context, exampleString: String, number: Int) {
        val res = context.resources

        val picture = BitmapFactory.decodeResource(res, R.drawable.icon_exercise)
        val title = res.getString(R.string.message_notification_title, exampleString)
        val text = res.getString(R.string.message_notification_text, exampleString)

        val builder = NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_VIBRATE) // Set appropriate defaults for the notification light, sound, and vibration
                .setSmallIcon(R.drawable.ic_stat_message)
                .setContentTitle(title)
                .setContentText(text)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Use a default priority (recognized on devices running Android 4.1 or later)
                .setLargeIcon(picture)
                .setTicker(exampleString)
                .setNumber(number)

                // If this notification relates to a past or upcoming event,
                // you should set the relevant time information using the setWhen method below.
                // If this call is omitted, the notification's timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or upcoming event.
                // The sole argument to this method should be the notification timestamp in milliseconds.
                .setWhen(10000)

                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                                PendingIntent.FLAG_UPDATE_CURRENT)
                )

                // Show expanded text content on devices running Android 4.1 or later.
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(text)
                        .setBigContentTitle(title)
                        .setSummaryText("В здоровом теле здоровый дух"))

                .addAction(
                        R.drawable.ic_share,
                        res.getString(R.string.action_share),
                        PendingIntent.getActivity(
                                context,
                                0,
                                Intent.createChooser(Intent(Intent.ACTION_SEND)
                                        .setType("text/plain")
                                        .putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"),
                                PendingIntent.FLAG_UPDATE_CURRENT))
//                .addAction(
//                        R.drawable.ic_action,
//                        res.getString(R.string.action_reply),
//                        null)
                .setAutoCancel(true)

        notify(context, builder.build())
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification)
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification)
        }
    }

    /**
     * Cancels any notifications of this type previously shown using [.notify].
     */
    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }

    fun saveToggleSwitchNotify(isChecked: Boolean) {
        switcher = isChecked
    }

    fun saveSelectedLanguage(id: Int) {
        languageId = id
    }
}
