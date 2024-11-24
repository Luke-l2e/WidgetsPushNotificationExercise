package edu.hhn.widgetspushnotifications.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import edu.hhn.widgetspushnotifications.R

object Widget : GlanceAppWidget() {
    val countKey = intPreferencesKey("count")

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent(count = currentState(key = countKey) ?: 0)
        }
    }
}

@Composable
fun WidgetContent(count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = GlanceModifier
            .background(ColorProvider(Color(67, 146, 165, 255)))
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
                text = count.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(Color.White),
                    fontSize = 26.sp
                )
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
                modifier = GlanceModifier.clickable(actionRunCallback<ResetActionCallback>())
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
fun PreviewWidget() {
    WidgetContent(count = 0)
}


class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[Widget.countKey] ?: 0
            prefs[Widget.countKey] = currentCount + 1
        }
        Widget.update(context, glanceId)
    }
}

class DecrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[Widget.countKey] ?: 0
            if (currentCount > 0) prefs[Widget.countKey] = currentCount - 1
        }
        Widget.update(context, glanceId)
    }
}

class ResetActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[Widget.countKey] = 0
        }
        Widget.update(context, glanceId)
    }
}

class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = Widget
}