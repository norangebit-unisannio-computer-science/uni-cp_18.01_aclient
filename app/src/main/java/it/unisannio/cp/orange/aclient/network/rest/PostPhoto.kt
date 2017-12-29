package it.unisannio.cp.orange.aclient.network.rest

import org.restlet.representation.FileRepresentation
import org.restlet.resource.ClientResource
import android.os.AsyncTask
import org.restlet.data.MediaType
import java.io.File
import java.io.IOException


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 29/12/17
 *  Last Modified: $file.lastModified
 */


class PostPhoto : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String): String? {
        val cr: ClientResource
        val filename = File(params[0]).name
        cr = ClientResource("${Path.ip}/${params[1]}/photo/$filename")
        var response: String? = null
        val payload = FileRepresentation(File(params[0]), MediaType.IMAGE_ALL)
        try {
            response = cr.post(payload).text
        } catch (e: IOException) {
            val text = "Error: " + cr.status.code + " - " + cr.status.description + " - " + cr.status.reasonPhrase
        }

        return response
    }

}