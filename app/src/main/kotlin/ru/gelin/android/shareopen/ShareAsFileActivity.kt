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
public class ShareAsFileActivity : Activity() {

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
