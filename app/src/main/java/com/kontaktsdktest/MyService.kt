package com.kontaktsdktest

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.NotificationCompat
import android.util.Log

class MyService : IntentService("Some Service") {
    override fun onHandleIntent(p0: Intent?) {

        Log.i("ed", "service started")

        val builder = NotificationCompat.Builder(this)
        builder.setSmallIcon(R.drawable.notification_template_icon_bg)
        builder.setContentTitle("Some Service started...")
        builder.setContentText("...and fucks given :(")
        builder.setAutoCancel(true)

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(1, builder.build())

    }


}
