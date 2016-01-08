package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Shares the opened link.
 *  Expects the incoming intent to be ACTION_VIEW and having a Data containing a link.
 *  Produces ACTION_SEND intent with EXTRA_TEXT taken as string representation of Data of the input intent.
 */
public class ShareAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = viewToSendText(intent)
        if (null == newIntent) {
            Log.d(TAG, "Cannot share opened link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_share_link, Toast.LENGTH_LONG).show()
            finish()
        }

        startActivity(Intent.createChooser(newIntent, title))
        finish()
    }

}
