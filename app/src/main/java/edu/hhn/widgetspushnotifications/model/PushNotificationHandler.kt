package edu.hhn.widgetspushnotifications.model

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import edu.hhn.widgetspushnotifications.R

private const val CHANNEL_ID = "notification_channel_id"
private const val CHANNEL_NAME = "Notifications"
private const val CHANNEL_DESCRIPTION = "Channel for notifications"
private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT

/**
 * Requests notification permissions from the user if not already granted.
 *
 * @param context the application context
 * @param notificationPermissionLauncher a launcher to handle the notification permission request
 */
fun askForNotificationPermission(
    context: Context,
    notificationPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

/**
 * Creates a notification channel for the application if it does not already exist.
 *
 * @param context the application context
 */
fun createNotificationChannel(context: Context) {
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            CHANNEL_IMPORTANCE
        ).apply {
            description = CHANNEL_DESCRIPTION
        }
        notificationManager.createNotificationChannel(channel)
    }
}

/**
 * Sends a notification using the specified title and text.
 *
 * @param context the application context
 * @param contentTitle the title of the notification
 * @param contentText the content text of the notification
 */
fun sendNotification(context: Context, contentTitle: String, contentText: String) {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}