package it.unisannio.cp.orange.aclient.services

import android.app.Notification
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.activities.DetailFlashMobActivity
import it.unisannio.cp.orange.aclient.model.ListInstance
import java.sql.Timestamp


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 09/01/18
 *  Last Modified: $file.lastModified
 */


class AlarmJobService: JobService() {

    override fun onStartJob(params: JobParameters?): Boolean{
        val notifyIntent = Intent(this, DetailFlashMobActivity::class.java)
        val pos = params?.extras?.getInt(Util.KEY_POS, 0) ?: 0
        notifyIntent.putExtra(Util.KEY_POS, pos)
        val pending = PendingIntent.getActivity(this, Util.CODE_NOTIFY_START, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val fm = ListInstance.get(pos)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(fm.name)
                .setContentText("${fm.name}, start ${getString(R.string.now)}")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setVibrate(longArrayOf(200, 100))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notify = builder.build()
        val notifyManager = NotificationManagerCompat .from(this)
        notifyManager.notify(0, notify)

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(Util.ID_ALARM_JOB+pos)

        return true
    }

    override fun onStopJob(params: JobParameters) = false
}