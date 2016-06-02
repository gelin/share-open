/*
 * Copyright 2015-2016 Denis Nelubin.
 *
 * This file is part of Share&Open.
 *
 * Share&Open is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Share&Open is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Share&Open. If not, see http://www.gnu.org/licenses/.
 */

package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Opens the shared link.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT or EXTRA_STREAM.
 *  Searched the link in the shared text.
 *  Produces ACTION_VIEW intent with the Data set to the found link.
 */
class OpenAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent: Intent?
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            newIntent = sendTextToViewLink(intent)
        } else {
            newIntent = sendStreamToViewLink(this, intent)
        }
        if (null == newIntent) {
            Log.d(TAG, "Cannot open shared link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_link, Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
