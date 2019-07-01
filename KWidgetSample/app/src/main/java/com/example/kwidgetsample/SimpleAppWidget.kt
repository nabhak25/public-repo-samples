package com.example.kwidgetsample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews

class SimpleAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
// There may be multiple widgets active, so update all of them
        Log.i("Widget", "onUpdate")
        if (appWidgetIds != null) {
            for (widgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId)
            }
        }
    }

    private fun updateAppWidget(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        // Construct the RemoteViews object
        val views = RemoteViews(context?.packageName, R.layout.simple_app_widget)
        // Construct an Intent object includes web adresss.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=wFV0RF6ZFEQ"))
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent)
        // Instruct the widget manager to update the widget
        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.i("Widget", "onAppWidgetOptionsChanged")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        Log.i("Widget", "onDeleted")
        super.onDeleted(context, appWidgetIds)
    }

    override fun onDisabled(context: Context?) {
        Log.i("Widget", "onDisabled")
        super.onDisabled(context)
    }

    override fun onEnabled(context: Context?) {
        Log.i("Widget", "onEnabled")
        super.onEnabled(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("Widget", "onReceive")
        super.onReceive(context, intent)
    }

}