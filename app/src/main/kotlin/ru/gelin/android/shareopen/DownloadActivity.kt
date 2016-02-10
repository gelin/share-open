package ru.gelin.android.shareopen

import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
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
