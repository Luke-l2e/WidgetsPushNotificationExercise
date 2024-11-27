package edu.hhn.widgetspushnotifications.widget

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll
import edu.hhn.widgetspushnotifications.data.DataStoreManager

/**
 * Callback to increment the widgets counter by one
 */
class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        try {
            DataStoreManager.modifyCounter(context, 1)
            Widget().update(context, glanceId)
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("IncrementCounter", it) }
        }
    }
}

/**
 * Callback to decrement the widgets counter by one
 */
class DecrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        try {
            DataStoreManager.modifyCounter(context, -1)
            Widget().update(context, glanceId)
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("DecrementCounter", it) }
        }
    }
}

/**
 * Update all Widgets
 */
class UpdateActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Widget().updateAll(context)
    }
}