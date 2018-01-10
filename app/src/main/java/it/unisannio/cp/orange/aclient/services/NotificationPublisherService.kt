package it.unisannio.cp.orange.aclient.services

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.activities.DetailFlashMobActivity
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.util.Util
import java.sql.Timestamp


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 04/01/18
 *  Last Modified: $file.lastModified
 */


class NotificationPublisherService: IntentService("") {

    companion object {
        val NOW = R.string.now
        val SOON = R.string.soon
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("SERVICE", "${Timestamp(System.currentTimeMillis())}")
        val notifyIntent = Intent(this, DetailFlashMobActivity::class.java)
        val pos = intent?.getIntExtra(Util.KEY_POS, 0) ?: 0
        notifyIntent.putExtra(Util.KEY_POS, pos)
        val pending = PendingIntent.getActivity(this, Util.CODE_NOTIFY_START, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val fm = ListInstance.get(pos)

        val builder = Notification.Builder(this)
        builder.setContentTitle(fm.name)
        builder.setContentText("${fm.name}, start ${getString(intent?.getIntExtra(Util.KEY_TIME, NOW) ?: NOW)}")
        builder.setSmallIcon(R.drawable.ic_alarm)
        builder.setAutoCancel(true)
        builder.setContentIntent(pending)

        val notify = builder.build()
        val notifyManager = NotificationManagerCompat .from(this)
        notifyManager.notify(0, notify)
    }

}