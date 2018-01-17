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

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.MessageButtonBehaviour
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.Manifest
import android.app.Activity
import android.os.Bundle
import it.unisannio.cp.orange.aclient.R


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
    }
}