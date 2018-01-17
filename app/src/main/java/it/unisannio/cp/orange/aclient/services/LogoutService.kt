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