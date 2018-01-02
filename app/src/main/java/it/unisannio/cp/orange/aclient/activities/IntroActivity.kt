package it.unisannio.cp.orange.aclient.activities

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.MessageButtonBehaviour
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.Manifest
import android.app.Activity
import android.os.Bundle
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.fragments.LoginFragments


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 30/12/17
 *  Last Modified: $file.lastModified
 */


class IntroActivity: MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .title(getString(R.string.welcome))
                .description(getString(R.string.intro_slide1_description))
                .build(),
                MessageButtonBehaviour({ setResult(Activity.RESULT_CANCELED); finish()}, getString(R.string.skip)))

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.colorPrimary)
                .title(getString(R.string.permissions))
                .description(getString(R.string.intro_slide2_permissions))
                .possiblePermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .build())

        addSlide(LoginFragments())
    }
}