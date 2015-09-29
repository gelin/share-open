package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast


public class ShareAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_VIEW == intent.action && null != intent.data) {
            val newIntent = Intent(Intent.ACTION_SEND)
            newIntent.setType("text/plain")
            newIntent.putExtra(Intent.EXTRA_TEXT, intent.dataString)
            startActivity(Intent.createChooser(newIntent, title))
        } else {
            Log.d(TAG, "Cannot share opened link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_share_link, Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
