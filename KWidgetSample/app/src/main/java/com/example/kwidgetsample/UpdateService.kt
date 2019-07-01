package com.example.kwidgetsample

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import kotlin.random.Random

class UpdateService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val random = Random
        val randomInt = random.nextInt(100)
        val lastUpdate = "R: $randomInt"
        // Reaches the view on widget and displays the number
        val views = RemoteViews(packageName, R.layout.updating_widget)
        views.setTextViewText(R.id.updatingTv, lastUpdate)
        val theWidget = ComponentName(this, UpdatingWidget::class.java)
        val manager = AppWidgetManager.getInstance(this)
        manager.updateAppWidget(theWidget, views)

        return super.onStartCommand(intent, flags, startId)
    }

}