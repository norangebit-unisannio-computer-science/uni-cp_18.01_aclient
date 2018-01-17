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

package it.unisannio.cp.orange.aclient.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.services.NotificationPublisherService
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.getSettings


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 04/01/18
 *  Last Modified: $file.lastModified
 */
 
 
class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val settings = context?.getSettings(R.xml.pref_general)
        if(settings?.getBoolean(Util.SP_NT_FLASHMOB, true) ?: true){
            val service = Intent(context, NotificationPublisherService::class.java)
            service.putExtra(Util.KEY_POS, intent?.getIntExtra(Util.KEY_POS, 0))
            service.putExtra(Util.KEY_TIME, intent?.getIntExtra(Util.KEY_TIME, 0))
            context?.startService(service)
        }
    }

}