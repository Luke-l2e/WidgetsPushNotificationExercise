package edu.hhn.widgetspushnotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import edu.hhn.widgetspushnotifications.data.theme.WidgetsPushNotificationsExerciseTheme
import edu.hhn.widgetspushnotifications.view.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WidgetsPushNotificationsExerciseTheme {
                MainScreen(this, this.lifecycleScope)
            }
        }
    }
}