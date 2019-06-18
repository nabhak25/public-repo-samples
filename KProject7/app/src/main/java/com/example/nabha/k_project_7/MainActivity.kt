package com.example.nabha.k_project_7

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View

class MainActivity : AppCompatActivity() {

    companion object {
        const val ID = 100
    }

    private var numMessages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sendNotification(view: View) {
//        simpleNotification()
        bigPictureNotification()
    }


    private fun simpleNotification() {
        val builder = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("New Notification")
                .setContentText("You have a notification!")
                .setAutoCancel(true)

//        val intent = Intent(ACTION_VIEW, Uri.parse("https://www.androidauthority.com/"))
        val intent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)


        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ID, builder.build())
    }

    private fun bigPictureNotification() {
        val builder = NotificationCompat.Builder(this, "channel_id")
        builder.setContentTitle("New Notification")
                .setContentText("You have a notification")
                .setTicker("Notification!!")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
        builder.setNumber(++numMessages)

        val inboxStyle = NotificationCompat.InboxStyle()
        val events = arrayOf("1st", "2nd", "3rd",
                                            "4th", "5th", "6th")
        inboxStyle.setBigContentTitle("Big View Notification")
        for (item in events) {
            inboxStyle.addLine(item)
        }
        builder.setStyle(inboxStyle)

        val intent = Intent(this, SecondActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(SecondActivity::class.java)
        /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(intent)
        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ID, builder.build())

    }

}
