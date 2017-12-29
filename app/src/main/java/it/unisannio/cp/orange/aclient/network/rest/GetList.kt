package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask
import android.util.Log

import com.google.gson.Gson

import org.restlet.resource.ClientResource
import org.restlet.resource.ResourceException

import java.util.Arrays

/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 16/12/17
 *  Last Modified: $file.lastModified
 */


class GetList : AsyncTask<String, Void, Array<String>>() {

    override fun doInBackground(vararg strings: String): Array<String>?{
        val gson = Gson()
        try {
            val cr = ClientResource(strings[0])
            val json = cr.get().text
            return gson.fromJson(json, Array<String>::class.java)
        } catch (e: ResourceException) {
        }

        return null
    }

    override fun onPostExecute(strings: Array<String>?) {
        Log.d("REST", Arrays.toString(strings))
        strings?.forEach {  GetFlashMob().execute("${Path.ip}/$it") }
    }
}
