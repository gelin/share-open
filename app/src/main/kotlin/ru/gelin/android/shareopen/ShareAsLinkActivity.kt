package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle


public class ShareAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_VIEW == intent.action && null != intent.data) {
            val newIntent = Intent(Intent.ACTION_SEND)
            newIntent.setType("text/plain")
            newIntent.putExtra(Intent.EXTRA_TEXT, intent.dataString)
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
