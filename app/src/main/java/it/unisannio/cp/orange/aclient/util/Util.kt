package it.unisannio.cp.orange.aclient.util

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */


class Util {
    companion object {
        fun checkPermission(context: Context, vararg permission: String) = permission.map { ContextCompat.checkSelfPermission(context, it) }
                .none { it == PackageManager.PERMISSION_DENIED }
    }
}