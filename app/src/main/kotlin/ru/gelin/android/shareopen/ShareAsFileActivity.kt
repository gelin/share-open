package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle


public class ShareAsFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()
        if (Intent.ACTION_VIEW == intent.getAction() && null != intent.getData()) {
            val newIntent = Intent(Intent.ACTION_SEND)
            newIntent.setType(intent.getType() ?: "*/*")
            newIntent.putExtra(Intent.EXTRA_STREAM, intent.getData())
            startActivity(Intent.createChooser(newIntent, getTitle()))
        }
        finish()
    }

}
