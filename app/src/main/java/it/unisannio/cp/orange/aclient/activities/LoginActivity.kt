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

package it.unisannio.cp.orange.aclient.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import it.unisannio.cp.orange.aclient.Presenters.LoginPresenter
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.contracts.LoginContract
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.change
import it.unisannio.cp.orange.aclient.util.getSettings
import it.unisannio.cp.orange.aclient.util.toast
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    val presenter: LoginContract.Presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginButton.setOnClickListener { presenter.checkLogin(etUserName.text.toString(), etPass.text.toString())}
    }

    override fun saveAndExit(result: Boolean) {
        val res = if(result){
            val settings = getSettings(R.xml.pref_general)
            settings.change {
                putString(Util.KEY_USER, etUserName.text.toString())
                putString(Util.KEY_PASSWORD, etPass.text.toString())
                putBoolean(Util.KEY_LOGIN, true)
            }
            R.string.login_ok
        } else R.string.login_nope
        toast(res)
        finish()
    }
}
