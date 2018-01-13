package it.unisannio.cp.orange.aclient.services

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.FileProvider
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.toast
import org.restlet.resource.ClientResource
import org.restlet.resource.ResourceException
import java.io.File
import java.io.FileOutputStream
import kotlin.system.exitProcess
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 11/01/18
 *  Last Modified: $file.lastModified
 */


class DownloadService: IntentService("download") {
    override fun onHandleIntent(intent: Intent?) {
        val outFile = intent?.getSerializableExtra("file") as File
        val url = intent.getStringExtra("url")
        val cr = ClientResource(url)

        try {
            cr.get().write(FileOutputStream(outFile))
        }catch (e: ResourceException) {
            toast(R.string.download_error)
            exitProcess(1)
        }

        val openPic = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(this, Util.AUTHORITY, outFile)
        openPic.setDataAndType(uri, "image/*")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val pending = PendingIntent.getActivity(this, Util.CODE_NOTIFY_DOWNLOAD, openPic, PendingIntent.FLAG_UPDATE_CURRENT)

        val qApp = this.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        qApp.forEach {
            this.grantUriPermission(it.activityInfo.packageName, uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.download_complete))
                .setContentText(getString(R.string.click_to_open))
                .setSmallIcon(R.drawable.ic_download)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setVibrate(longArrayOf(200, 100))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notify = builder.build()
        val notifyManager = NotificationManagerCompat .from(this)
        notifyManager.notify(0, notify)
    }
}