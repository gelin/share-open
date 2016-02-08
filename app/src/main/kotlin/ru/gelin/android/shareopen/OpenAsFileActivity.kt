package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Opens the shared stream.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT or EXTRA_STREAM.
 *  Produces ACTION_VIEW intent with DataAndType taken from the input intent.
 */
class OpenAsFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent: Intent?
        if (intent.hasExtra(Intent.EXTRA_STREAM)) {
            newIntent = sendStreamToViewFile(intent)
        } else {
            newIntent = sendTextToViewFile(this, intent)
        }
        if (null == newIntent) {
            Log.d(TAG, "Cannot open shared file from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_file, Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
