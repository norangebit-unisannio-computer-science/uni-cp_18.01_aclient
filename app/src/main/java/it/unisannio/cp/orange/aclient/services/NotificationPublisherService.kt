/*
 * Copyright (c)  2018 Raffaele Mignone
 *
 *        This file is part of  A Client
 *
 *        A Client is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *        the Free Software Foundation, either version 2 of the License, or
 *        (at your option) any later version.
 *
 *        A Client is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 *        You should have received a copy of the GNU General Public License
 *        along with A Client.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unisannio.cp.orange.aclient.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.activities.DetailFlashMobActivity
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.getSettings


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
        val settings = getSettings(R.xml.pref_general)
        val notifyIntent = Intent(this, DetailFlashMobActivity::class.java)
        val pos = intent?.getIntExtra(Util.KEY_POS, 0) ?: 0
        notifyIntent.putExtra(Util.KEY_POS, pos)
        val pending = PendingIntent.getActivity(this, Util.CODE_NOTIFY_START, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val ringtone = settings.getString(Util.SP_NT_FLASHMOB_RING, null)
        val ringUri = if(ringtone != null) Uri.parse(ringtone)
                else RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val fm = ListInstance.get(pos)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(fm.name)
                .setContentText("${fm.name}, start ${getString(intent?.getIntExtra(Util.KEY_TIME, NOW) ?: NOW)}")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSound(ringUri)
        if(settings.getBoolean(Util.SP_NT_FLASHMOB_VIBRATE, true))
            builder.setVibrate(longArrayOf(200, 100))

        val notify = builder.build()
        val notifyManager = NotificationManagerCompat .from(this)
        notifyManager.notify(0, notify)
    }

}