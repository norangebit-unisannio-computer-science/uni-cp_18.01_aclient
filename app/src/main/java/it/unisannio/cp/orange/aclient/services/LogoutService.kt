package it.unisannio.cp.orange.aclient.services

import android.app.IntentService
import android.content.Intent
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.change
import it.unisannio.cp.orange.aclient.util.getSettings
import it.unisannio.cp.orange.aclient.util.toast


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 16/01/18
 *  Last Modified: $file.lastModified
 */
 
 
class LogoutService: IntentService("logout"){
    override fun onHandleIntent(intent: Intent?) {
        val settings = getSettings(R.xml.pref_general)
        settings.change {
            remove(Util.KEY_USER)
            remove(Util.KEY_PASSWORD)
            remove(Util.KEY_LOGIN)
        }
    }
}