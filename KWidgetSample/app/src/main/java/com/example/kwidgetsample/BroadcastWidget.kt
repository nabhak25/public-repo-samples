package com.example.kwidgetsample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

class BroadcastWidget : AppWidgetProvider() {

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
        val views = RemoteViews(context?.packageName, R.layout.broadcast_widget)
        // Construct an Intent which is pointing this class.
        val intent = Intent(context, BroadcastWidget::class.java)
        intent.action = ACTION_SIMPLEAPPWIDGET
        // And this time we are sending a broadcast with getBroadcast
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.broadcastWidget, pendingIntent)
        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (ACTION_SIMPLEAPPWIDGET == intent?.action) {
            mCounter += 1
            // Construct the RemoteViews object
            val views = RemoteViews(context?.packageName, R.layout.broadcast_widget)
            views.setTextViewText(R.id.broadcastWidget, mCounter.toString())
            // This time we dont have widgetId. Reaching our widget with that way.
            val appWidget = ComponentName(context, BroadcastWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    companion object {
        const val ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGETSAMPLE"
        var mCounter = 0
    }
}