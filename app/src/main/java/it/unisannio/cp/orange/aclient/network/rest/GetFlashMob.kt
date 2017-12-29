package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask
import android.util.Log

import com.google.gson.Gson

import org.restlet.resource.ClientResource


import commons.FlashMob
import it.unisannio.cp.orange.aclient.model.ListInstance
import org.restlet.data.ChallengeScheme
import org.restlet.resource.ResourceException

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
        try{
            cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "debug", "debug")
            val json = cr.get().text
            Log.d("REST_TEXT", json)
            return gson.fromJson(json, FlashMob::class.java)
        }catch (e: ResourceException){

        }
        return null
    }

    override fun onPostExecute(fm: FlashMob?) {

        if (fm != null){
            Log.d("REST", fm.name)
            ListInstance.add(fm)
        }
    }
}
