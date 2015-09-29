package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast


public class ShareAsFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_VIEW == intent.action && null != intent.data) {
            val newIntent = Intent(Intent.ACTION_SEND)
            newIntent.setType(intent.type ?: "*/*")
            newIntent.putExtra(Intent.EXTRA_STREAM, intent.data)
            startActivity(Intent.createChooser(newIntent, title))
        } else {
            Log.d(TAG, "Cannot share opened file from intent: " + intent)
            Toast.makeText(this, R.string.cannot_share_file, Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
