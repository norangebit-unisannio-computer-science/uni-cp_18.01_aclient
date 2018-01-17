/*
 * Copyright (c)  2018 Raffaele Mignone
 *
 *        This file is part of  A Client
 *
 *        A Client is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *        the Free Software Foundation, either version 2 of the License, or
 *        (at your option) any later version.
 *
 *        A Client is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 *        You should have received a copy of the GNU General Public License
 *        along with A Client.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unisannio.cp.orange.aclient.network.rest

import org.restlet.representation.FileRepresentation
import org.restlet.resource.ClientResource
import android.os.AsyncTask
import android.util.Log
import org.restlet.data.ChallengeScheme
import org.restlet.data.MediaType
import org.restlet.resource.ResourceException
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
        val filename = File(params[2]).name
        cr = ClientResource("${Path.ip}/${params[3]}/photo/$filename")
        var response: String? = null
        val payload = FileRepresentation(File(params[2]), MediaType.IMAGE_ALL)
        try {
            cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, params[0], params[1])
            response = cr.post(payload).text
        } catch (e: IOException) {
            Log.e("ERROR", e.message)
        } catch (e: ResourceException){
            Log.e("ERROR", "${cr.status.code}: ${cr.status.description} - ${cr.status.reasonPhrase}")
        }

        return response
    }

}