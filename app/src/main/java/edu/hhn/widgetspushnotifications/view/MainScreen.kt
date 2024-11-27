package edu.hhn.widgetspushnotifications.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import edu.hhn.firebaseconnector.NotificationHelper
import edu.hhn.widgetspushnotifications.R
import edu.hhn.widgetspushnotifications.data.Colors
import edu.hhn.widgetspushnotifications.data.DataStoreManager
import kotlinx.coroutines.launch

private const val CHANNEL_ID = "notification_channel_id"
private const val CHANNEL_NAME = "Notifications"
private const val CHANNEL_DESCRIPTION = "Channel for notifications"
private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT

// TODO: FCM Broadcast Listener
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    var counter by remember { mutableIntStateOf(0) }
    val fieldValue = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Notification permission is required for this app",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        setupNotification(context, notificationPermissionLauncher)
        lifecycleScope.launch {
            DataStoreManager.getCounter(context).collect {
                counter = it
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
            TopAppBar(
                title = { Text("Broadcaster") },
                colors = Colors.getTopAppBarColors()
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                Text(
                    text = "Current count",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = counter.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Column {
                TextField(
                    value = fieldValue.value,
                    onValueChange = { fieldValue.value = it },
                    singleLine = true,
                    label = { Text("Custom message") }, trailingIcon = {
                        Icon(
                            Icons.AutoMirrored.Rounded.Send,
                            contentDescription = "Send",
                            modifier = Modifier.clickable {
                                lifecycleScope.launch {
                                    try {
                                        NotificationHelper.sendNotification(
                                            "Textfield", fieldValue.value,
                                            callback = { isSuccess, message ->
                                                if (isSuccess) {
                                                    sendNotification(context, "Success", message)
                                                } else {
                                                    sendNotification(context, "Failed", message)
                                                }
                                            },
                                        )
                                        DataStoreManager.modifyCounter(context, 1)
                                    } catch (e: Exception) {
                                        snackbarHostState.showSnackbar("Unexpected Error")
                                    }
                                }
                            }
                        )
                    }, colors = Colors.getTextFieldColors(), modifier = Modifier.fillMaxWidth(.8f)
                )
            }
        }
    }
}

private fun setupNotification(
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
 * Create a notificationChannel
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
 * Creates a notification
 * @param context Context: Localcontext.current
 * @param contentTitle String: The title to show
 * @param contentText String: The text to show
 */
fun sendNotification(context: Context, contentTitle: String, contentText: String) {
    createNotificationChannel(context)
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    Handler(Looper.getMainLooper()).post {
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}