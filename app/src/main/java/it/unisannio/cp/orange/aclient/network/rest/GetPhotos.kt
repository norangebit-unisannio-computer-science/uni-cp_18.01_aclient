package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import it.unisannio.cp.orange.aclient.adapters.PicAdapter
import org.restlet.resource.ClientResource
import org.restlet.resource.ResourceException


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 29/12/17
 *  Last Modified: $file.lastModified
 */


class GetPhotos(val list: ArrayList<String>, val adapter: PicAdapter): AsyncTask<String, Void, Array<String>>() {

    override fun doInBackground(vararg params: String?): Array<String>? {
        val gson = Gson()
        val cr = ClientResource(params[0])
        try {
            val json = cr.get().text
            return gson.fromJson(json, Array<String>::class.java)
        } catch (e: ResourceException) {
            Log.e("ERROR", "${cr.status.code}: ${cr.status.description} - ${cr.status.reasonPhrase}")
        }

        return null
    }

    override fun onPostExecute(result: Array<String>?) {
        if (result != null)
            list.addAll(result.filter { it != "cover.jpg"})
        adapter.notifyDataSetChanged()
    }
}