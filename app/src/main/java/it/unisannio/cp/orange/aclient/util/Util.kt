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

package it.unisannio.cp.orange.aclient.util

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.widget.Toast


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */

fun SharedPreferences.change(func: SharedPreferences.Editor.()->SharedPreferences.Editor) = this.edit().func().apply()

fun FragmentManager.transaction(func: FragmentTransaction.()-> FragmentTransaction)= this.beginTransaction().func().commit()

fun Context.toast(resID: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resID, duration).show()

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show()

fun Context.checkPermission(vararg permission: String) = permission.map { ContextCompat.checkSelfPermission(this, it) }
        .none { it == PackageManager.PERMISSION_DENIED }

fun Context.getSettings(pref: Int, readAgain: Boolean=false): SharedPreferences{
    PreferenceManager.setDefaultValues(this, pref, readAgain)
    return PreferenceManager.getDefaultSharedPreferences(this)
}

class Util {
    companion object {

        val KEY_USER = "username"
        val KEY_PASSWORD = "password"
        val KEY_FIRST_LAUNCH = "first launch"
        val KEY_LOGIN = "login"
        val KEY_POS = "position"
        val KEY_TIME = "time"
        val KEY_FILE = "file"
        val KEY_URL = "url"

        val SP_NT_DOWNLOAD = "notifications_downloads"
        val SP_NT_DOWNLOAD_RING = "notifications_downloads_ringtone"
        val SP_NT_DOWNLOAD_VIBRATE = "notifications_downloads_vibrate"
        val SP_NT_FLASHMOB = "notifications_flashmob"
        val SP_NT_FLASHMOB_RING = "notifications_flashmob_ringtone"
        val SP_NT_FLASHMOB_VIBRATE = "notifications_flashmob_vibrate"
        val SP_BATTERY_SAVE = "battery_save"

        val CODE_INTRO = 1000
        val CODE_UPLOAD = 1001
        val CODE_ALARM_RECEIVER = 1002
        val CODE_NOTIFY_START = 1003
        val CODE_NOTIFY_DOWNLOAD = 1004
        val CODE_CAMERA_PERMISSION = 2000
        val CODE_SD_PERMISSION = 2001

        val ID_ALARM_JOB = 3000

        val AUTHORITY = "it.unisannio.cp.orange.aclient.fileProvider"

    }
}