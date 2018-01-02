package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask

import com.google.gson.Gson

import org.restlet.resource.ClientResource


import commons.FlashMob
import it.unisannio.cp.orange.aclient.model.ListInstance

/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 16/12/17
 *  Last Modified: $file.lastModified
 */


internal class GetFlashMob : AsyncTask<String, Void, FlashMob>() {

    override fun doInBackground(vararg strings: String): FlashMob? {
        val gson = Gson()

        val cr = ClientResource(strings[0])
        val json = cr.get().text
        return gson.fromJson(json, FlashMob::class.java)
    }

    override fun onPostExecute(fm: FlashMob?) {
        if (fm != null)
            ListInstance.add(fm)
    }
}
