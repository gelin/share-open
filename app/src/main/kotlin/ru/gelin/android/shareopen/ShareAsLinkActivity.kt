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
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Shares the opened link.
 *  Expects the incoming intent to be ACTION_VIEW and having a Data containing a link.
 *  Produces ACTION_SEND intent with EXTRA_TEXT taken as string representation of Data of the input intent.
 */
class ShareAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = viewToSendText(intent)
        if (null == newIntent) {
            Log.d(TAG, "Cannot share opened link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_share_link, Toast.LENGTH_LONG).show()
        } else {
            startActivity(findActivityToStart(newIntent))
        }
        finish()
    }

    private val lengthenerComponent = ComponentName("ru.gelin.lengthener.android", "ru.gelin.lengthener.android.ShareActivity")

    private fun findActivityToStart(intent : Intent) : Intent {
        val lengthenerIntent = Intent(intent)
        lengthenerIntent.component = lengthenerComponent
        if (null != packageManager.resolveActivity(lengthenerIntent, PackageManager.MATCH_DEFAULT_ONLY)) {
            return lengthenerIntent
        } else {
            return Intent.createChooser(intent, title)
        }
    }

}
