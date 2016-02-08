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
