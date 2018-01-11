package it.unisannio.cp.orange.aclient.network.rest

import android.os.AsyncTask
import android.util.Log
import org.restlet.resource.ClientResource
import java.io.File
import java.io.FileOutputStream


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 10/01/18
 *  Last Modified: $file.lastModified
 */
 
 
class GetPhoto(val outFile: File): AsyncTask<String, Int, Any>() {

    override fun doInBackground(vararg params: String?) {
        Log.d("PARAM", params[0])
        val cr = ClientResource(params[0])
        cr.get().write(FileOutputStream(outFile))
        publishProgress(1)
    }
}