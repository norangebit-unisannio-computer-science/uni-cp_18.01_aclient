package it.unisannio.cp.orange.aclient.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import it.unisannio.cp.orange.aclient.services.NotificationPublisherService
import it.unisannio.cp.orange.aclient.util.Util


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 04/01/18
 *  Last Modified: $file.lastModified
 */
 
 
class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context, NotificationPublisherService::class.java)
        service.putExtra(Util.KEY_POS, intent?.getIntExtra(Util.KEY_POS, 0))
        service.putExtra(Util.KEY_TIME, intent?.getIntExtra(Util.KEY_TIME, 0))
        context?.startService(service)
    }

}