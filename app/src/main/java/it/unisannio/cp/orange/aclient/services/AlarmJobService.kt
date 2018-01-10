package it.unisannio.cp.orange.aclient.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import it.unisannio.cp.orange.aclient.util.Util


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 09/01/18
 *  Last Modified: $file.lastModified
 */


class AlarmJobService: JobService() {

    override fun onStopJob(params: JobParameters?): Boolean {
        val service = Intent(this, NotificationPublisherService::class.java)
        service.putExtra(Util.KEY_POS, params?.extras?.getInt(Util.KEY_POS))
        service.putExtra(Util.KEY_TIME, params?.extras?.getInt(Util.KEY_TIME))
        this.startService(service)
        return false
    }

    override fun onStartJob(params: JobParameters?) = true
}