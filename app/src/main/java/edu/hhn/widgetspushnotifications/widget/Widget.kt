package edu.hhn.widgetspushnotifications.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import edu.hhn.widgetspushnotifications.R
import edu.hhn.widgetspushnotifications.data.DataStoreManager
import kotlinx.coroutines.launch

class Widget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent(context)
        }
    }

    @Composable
    fun WidgetContent(context: Context) {
        var counter by remember { mutableIntStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                DataStoreManager.getCounter(context).collect {
                    counter = it
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxSize()
                .background(ColorProvider(Color(67, 146, 165, 255))).cornerRadius(5.dp)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    provider = ImageProvider(R.drawable.round_remove_24),
                    contentDescription = "Decrease",
                    modifier = GlanceModifier.clickable(actionRunCallback<DecrementActionCallback>())
                )
                Text(
                    text = counter.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = ColorProvider(Color.White),
                        fontSize = 26.sp
                    ), modifier = GlanceModifier.padding(horizontal = 5.dp)
                )
                Image(
                    provider = ImageProvider(R.drawable.round_add_24),
                    contentDescription = "Increase",
                    modifier = GlanceModifier.clickable(actionRunCallback<IncrementActionCallback>())
                )
            }
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    provider = ImageProvider(R.drawable.round_send_24),
                    contentDescription = "Send",
                    modifier = GlanceModifier.clickable(actionRunCallback<UpdateActionCallback>())
                )
            }
        }
    }
}