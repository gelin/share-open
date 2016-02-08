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
