package it.unisannio.cp.orange.aclient.fragments

import agency.tango.materialintroscreen.SlideFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.change
import kotlinx.android.synthetic.main.login.*


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */
 
 
class LoginFragments: SlideFragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater?.inflate(R.layout.login, container, false)
    }

    override fun canMoveFurther(): Boolean {
        val sp = context.getSharedPreferences(Util.SP_SETTINGS, Context.MODE_PRIVATE)
        sp.change {
            putString(Util.KEY_USER, etUserName.text.toString())
            putString(Util.KEY_PASSWORD, etPass.text.toString())
            putBoolean(Util.KEY_LOGIN, true)
        }
        return true
    }

    override fun backgroundColor() = R.color.colorPrimary

    override fun buttonsColor() = R.color.colorAccent
}