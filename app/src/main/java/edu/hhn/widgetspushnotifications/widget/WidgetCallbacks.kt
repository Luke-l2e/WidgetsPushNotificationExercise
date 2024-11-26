package edu.hhn.widgetspushnotifications.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll
import edu.hhn.widgetspushnotifications.data.DataStoreManager

class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        DataStoreManager.modifyCounter(context, 1)
        Widget().updateAll(context)
    }
}

class DecrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        DataStoreManager.modifyCounter(context, -1)
        Widget().updateAll(context)
    }
}

class UpdateActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Widget().updateAll(context)
    }
}