package it.unisannio.cp.orange.aclient.Presenters

import android.os.AsyncTask

import it.unisannio.cp.orange.aclient.contracts.LoginContract
import it.unisannio.cp.orange.aclient.network.rest.Path
import org.restlet.data.ChallengeScheme

import org.restlet.resource.ClientResource
import org.restlet.resource.ResourceException

/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 16/12/17
 *  Last Modified: $file.lastModified
 */


class LoginPresenter(val view: LoginContract.View) : AsyncTask<String, Void, Boolean>(), LoginContract.Presenter{

    override fun checkLogin(userName: String, password: String) {
        this.execute(userName, password)
    }

    override fun doInBackground(vararg params: String): Boolean{
        var status = 200

        try {
            val cr = ClientResource("${Path.ip}/users")
            cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, params[0], params[1])
            cr.get()
        } catch (e: ResourceException) {
            status = e.status.code
        }

        return status == 200
    }

    override fun onPostExecute(result: Boolean?) = view.saveAndExit(result!!)
}
