package edu.hhn.widgetspushnotifications.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * A receiver class for managing the lifecycle of a GlanceAppWidget.
 *
 * This class acts as a bridge between the system and the `GlanceAppWidget`, enabling updates
 * and handling events related to the app widget. It extends `GlanceAppWidgetReceiver`.
 */
class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = Widget()
}