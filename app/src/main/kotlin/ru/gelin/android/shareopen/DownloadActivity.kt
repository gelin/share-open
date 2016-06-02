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
import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Starts downloading the opening file.
 */
class DownloadActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (null == intent.data) {
            Log.d(TAG, "Cannot download intent: " + intent)
            Toast.makeText(this, R.string.cannot_download, Toast.LENGTH_LONG).show()
        } else {
            val manager = getSystemService(DOWNLOAD_SERVICE) as? DownloadManager
            if (null == manager) {
                Log.d(TAG, "Cannot download, no download manager")
                Toast.makeText(this, R.string.cannot_download, Toast.LENGTH_LONG).show()
            } else {
                manager.enqueue(DownloadManager.Request(intent.data))
                Toast.makeText(this, R.string.download_started, Toast.LENGTH_LONG).show()
            }
        }
        finish()
    }

}
