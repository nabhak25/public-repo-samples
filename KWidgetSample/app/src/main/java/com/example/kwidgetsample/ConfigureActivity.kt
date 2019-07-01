package com.example.kwidgetsample

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_configure.*

class ConfigureActivity : AppCompatActivity() {

    private lateinit var appWidgetManager: AppWidgetManager
    private lateinit var views: RemoteViews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.activity_configure)

        appWidgetManager = AppWidgetManager.getInstance(this)
        views = RemoteViews(packageName, R.layout.configurable_widget)

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppwidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        if (mAppwidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        btAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(etUrl.text.toString()))
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            views.setOnClickPendingIntent(R.id.widgetView, pendingIntent)
            appWidgetManager.updateAppWidget(mAppwidgetId, views)
            val resultValue = Intent()
            // Set the results as expected from a 'configure activity'.
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppwidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }


    }

    companion object {
        var mAppwidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    }
}
