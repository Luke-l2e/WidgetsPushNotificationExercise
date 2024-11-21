package edu.hhn.widgetspushnotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.hhn.widgetspushnotifications.ui.theme.WidgetsPushNotificationsExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WidgetsPushNotificationsExerciseTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        val counter = remember { mutableIntStateOf(0) }
        val fieldValue = remember { mutableStateOf("") }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(
                        text = "Current count",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    val text = counter.intValue.toString()
                    Text(text = text)
                }
                Column {
                    TextField(
                        value = fieldValue.value,
                        onValueChange = { fieldValue.value = it },
                        singleLine = true, label = { Text("Custom message") }
                    )
                }
                Button(
                    onClick = { counter.intValue++ }, colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) { Text("Senden") }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewTest(){
        MainScreen()
    }
}