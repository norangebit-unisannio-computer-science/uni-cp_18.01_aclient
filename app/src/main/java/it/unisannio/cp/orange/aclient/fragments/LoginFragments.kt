package it.unisannio.cp.orange.aclient.fragments

import agency.tango.materialintroscreen.SlideFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.unisannio.cp.orange.aclient.R


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */
 
 
class LoginFragments: SlideFragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.login, container, false)
        return v
    }

    override fun backgroundColor(): Int {
        return R.color.colorPrimary
    }

    override fun buttonsColor(): Int {
        return R.color.colorAccent
    }
}