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
 *  Shares the opened file.
 *  Expects the incoming intent to be ACTION_VIEW and having a file or content Data.
 *  Produces ACTION_SEND intent with EXTRA_STREAM taken from Data of the input intent.
 */
class ShareAsFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = viewToSendStream(intent)
        if (null == newIntent) {
            Log.d(TAG, "Cannot share opened file from intent: " + intent)
            Toast.makeText(this, R.string.cannot_share_file, Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
