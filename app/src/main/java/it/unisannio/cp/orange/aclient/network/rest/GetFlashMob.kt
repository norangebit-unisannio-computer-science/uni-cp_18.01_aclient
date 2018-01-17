package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask
import android.util.Log

import com.google.gson.GsonBuilder

import org.restlet.resource.ClientResource


import commons.FlashMob
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.model.commons.Code
import it.unisannio.cp.orange.aclient.model.commons.FlashMobException
import org.restlet.resource.ResourceException

/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 16/12/17
 *  Last Modified: $file.lastModified
 */


internal class GetFlashMob : AsyncTask<String, Void, FlashMob>() {

    override fun doInBackground(vararg strings: String): FlashMob? {
        val gson = GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create()
        var fm: FlashMob? = null

        val cr = ClientResource(strings[0])
        try {
            val json = cr.get().text
            if(cr.status.code == Code.NOT_FOUND)
                throw FlashMobException(Code.NOT_FOUND)
            fm = gson.fromJson(json, FlashMob::class.java)
        }catch (e: ResourceException){
            Log.e("ERROR", "${cr.status.code}: ${cr.status.description} - ${cr.status.reasonPhrase}")
        }catch (e: FlashMobException){
            Log.e("ERROR", "${e.code}: ${e.message}")
        }
        return fm
    }

    override fun onPostExecute(fm: FlashMob?) {
        if (fm != null)
            ListInstance.add(fm)
    }
}
