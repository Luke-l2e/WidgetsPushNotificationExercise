package edu.hhn.widgetspushnotifications.view

import android.content.ActivityNotFoundException
import android.content.Context
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.core.IOException
import androidx.lifecycle.LifecycleCoroutineScope
import edu.hhn.firebaseconnector.NotificationHelper
import edu.hhn.widgetspushnotifications.data.Colors
import edu.hhn.widgetspushnotifications.data.DataStoreManager
import edu.hhn.widgetspushnotifications.model.askForNotificationPermission
import edu.hhn.widgetspushnotifications.model.createNotificationChannel
import edu.hhn.widgetspushnotifications.model.initializeFirebaseCloudMessaging
import kotlinx.coroutines.launch

private const val PERMISSION_GRANTED = "Permission granted"
private const val PERMISSION_REQUIRED = "Notification permission is required for this app"

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
            val message = if (isGranted) PERMISSION_GRANTED else PERMISSION_REQUIRED
            lifecycleScope.launch { snackbarHostState.showSnackbar(message) }
        }
    )

    LaunchedEffect(Unit) {
        try {
            askForNotificationPermission(context, notificationPermissionLauncher)
        } catch (e: ActivityNotFoundException) {
            e.localizedMessage?.let { snackbarHostState.showSnackbar(it) }
        }
        createNotificationChannel(context)
        initializeFirebaseCloudMessaging(lifecycleScope, snackbarHostState, context)
        lifecycleScope.launch {
            try {
                DataStoreManager.getCounter(context).collect {
                    counter = it
                }
            } catch (e: IOException) {
                e.localizedMessage?.let { snackbarHostState.showSnackbar(it) }
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
                        TrailingIconToSendText(
                            lifecycleScope,
                            fieldValue, counter,
                            snackbarHostState,
                            context
                        )
                    }, colors = Colors.getTextFieldColors(), modifier = Modifier.fillMaxWidth(.8f)
                )
            }
        }
    }
}

/**
 * The send-icon, allowing the user to send the text from the text field.
 *
 * @param lifecycleScope the lifecycle-aware coroutine scope for asynchronous operations
 * @param fieldValue a mutable state representing the current text input value
 * @param counter an integer counter to include in the notification
 * @param snackbarHostState the SnackbarHostState used to show feedback messages
 * @param context the application context
 */
@Composable
private fun TrailingIconToSendText(
    lifecycleScope: LifecycleCoroutineScope,
    fieldValue: MutableState<String>, counter: Int,
    snackbarHostState: SnackbarHostState,
    context: Context
) {
    Icon(
        Icons.AutoMirrored.Rounded.Send,
        contentDescription = "Send",
        modifier = Modifier.clickable {
            lifecycleScope.launch {
                try {
                    NotificationHelper.sendNotification(
                        "Counter: $counter", fieldValue.value,
                        callback = { isSuccess, message ->
                            lifecycleScope.launch {
                                snackbarHostState.showSnackbar(
                                    message
                                )
                            }
                        },
                    )
                    DataStoreManager.modifyCounter(context, 1)
                } catch (e: Exception) {
                    e.localizedMessage?.let { snackbarHostState.showSnackbar(it) }
                }
            }
        }
    )
}