package edu.hhn.widgetspushnotifications.model

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch

/**
 * Firebase Messaging Service for handling incoming Firebase Cloud Messaging (FCM) broadcasts.
 */
class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: "Default Title"
            val message = remoteMessage.data["message"] ?: "Default Message"
            sendNotification(this, title, message)
        }
        remoteMessage.notification?.let {
            sendNotification(this, it.title ?: "Notification", it.body ?: "Message body")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}

/**
 * Initializes Firebase Cloud Messaging (FCM) for the application, including subscription to the topic "broadcast".
 *
 * @param lifecycleScope the lifecycle-aware coroutine scope for performing asynchronous operations
 * @param snackbarHostState the SnackbarHostState to display messages to the user
 * @param context the application context
 */
fun initializeFirebaseCloudMessaging(
    lifecycleScope: LifecycleCoroutineScope,
    snackbarHostState: SnackbarHostState, context: Context
) {
    FirebaseApp.initializeApp(context)
    Firebase.messaging.subscribeToTopic("broadcast")
        .addOnCompleteListener { task ->
            var message = "FCM subscribed"
            if (!task.isSuccessful) {
                message = "FCM subscription failed"
            }
            lifecycleScope.launch { snackbarHostState.showSnackbar(message) }
        }
}
