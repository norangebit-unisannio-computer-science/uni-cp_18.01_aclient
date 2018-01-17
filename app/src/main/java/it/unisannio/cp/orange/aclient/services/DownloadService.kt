package it.unisannio.cp.orange.aclient.services

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
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
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.util.Log
import it.unisannio.cp.orange.aclient.util.getSettings


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 11/01/18
 *  Last Modified: $file.lastModified
 */


class DownloadService: IntentService("download") {
    override fun onHandleIntent(intent: Intent?) {

        val settings = this.getSettings(R.xml.pref_general)

        val outFile = intent?.getSerializableExtra("file") as File
        val url = intent.getStringExtra("url")
        val cr = ClientResource(url)

        try {
            cr.get().write(FileOutputStream(outFile))
        }catch (e: ResourceException) {
            Log.e("ERROR", "${cr.status.code}: ${cr.status.description} - ${cr.status.reasonPhrase}")
            toast(R.string.download_error)
            exitProcess(1)
        }

        if(settings.getBoolean(Util.SP_NT_DOWNLOAD, true))
            sendNotification(settings, outFile)
    }

    fun sendNotification(settings: SharedPreferences, outFile: File){
        val openPic = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(this, Util.AUTHORITY, outFile)
        openPic.setDataAndType(uri, "image/*")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val pending = PendingIntent.getActivity(this, Util.CODE_NOTIFY_DOWNLOAD, openPic, PendingIntent.FLAG_UPDATE_CURRENT)

        val ringtone = settings.getString(Util.SP_NT_DOWNLOAD_RING, null)
        val ringUri = if(ringtone != null) Uri.parse(ringtone)
        else RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.download_complete))
                .setContentText(getString(R.string.click_to_open))
                .setSmallIcon(R.drawable.ic_download)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSound(ringUri)
        if(settings.getBoolean(Util.SP_NT_DOWNLOAD_VIBRATE, true))
            builder.setVibrate(longArrayOf(200, 100))

        val notify = builder.build()
        val notifyManager = NotificationManagerCompat .from(this)
        notifyManager.notify(0, notify)
    }
}