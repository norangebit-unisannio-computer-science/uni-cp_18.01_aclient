package it.unisannio.cp.orange.aclient.util

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */

fun SharedPreferences.change(func: SharedPreferences.Editor.()->SharedPreferences.Editor) = this.edit().func().apply()

class Util {
    companion object {

        val KEY_USER = "username"
        val KEY_PASSWORD = "password"
        val KEY_FIRST_LAUNCH = "first launch"
        val KEY_LOGIN = "login"
        val KEY_POS = "position"

        val SP_SETTINGS = "settings"

        val CODE_INTRO = 1000
        val CODE_UPLOAD = 1001
        val CODE_CAMERA_PERMISSION = 2000

        val AUTHORITY = "it.unisannio.cp.orange.aclient.fileProvider"

        fun checkPermission(context: Context, vararg permission: String) = permission.map { ContextCompat.checkSelfPermission(context, it) }
                .none { it == PackageManager.PERMISSION_DENIED }
    }
}