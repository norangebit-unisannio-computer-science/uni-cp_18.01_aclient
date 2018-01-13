package it.unisannio.cp.orange.aclient.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */

fun SharedPreferences.change(func: SharedPreferences.Editor.()->SharedPreferences.Editor) = this.edit().func().apply()

fun Context.toast(resID: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resID, duration).show()

fun Context.checkPermission(vararg permission: String) = permission.map { ContextCompat.checkSelfPermission(this, it) }
        .none { it == PackageManager.PERMISSION_DENIED }

class Util {
    companion object {

        val KEY_USER = "username"
        val KEY_PASSWORD = "password"
        val KEY_FIRST_LAUNCH = "first launch"
        val KEY_LOGIN = "login"
        val KEY_POS = "position"
        val KEY_BATTERY_SAVE = "battery save"
        val KEY_TIME = "time"
        val KEY_FILE = "file"
        val KEY_URL = "url"

        val SP_SETTINGS = "settings"

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