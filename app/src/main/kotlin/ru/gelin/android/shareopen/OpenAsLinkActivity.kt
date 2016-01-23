package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlin.text.Regex

/**
 *  Opens the shared link.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT.
 *  Searched the link in the shared text.
 *  Produces ACTION_VIEW intent with the Data set to the found link.
 */
public class OpenAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = sendTextToViewLink(intent)
        if (newIntent == null) {
            Log.d(TAG, "Cannot open shared link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_link, Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
