package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem


public class OpenToShare : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()
        if (Intent.ACTION_VIEW == intent.getAction() && null != intent.getData()) {
            val newIntent = Intent(Intent.ACTION_SEND)
            newIntent.setType("text/plain")     // TODO: take care about streaming data, pass it as EXTRA_STREAM
            newIntent.putExtra(Intent.EXTRA_TEXT, intent.getDataString())
            startActivity(Intent.createChooser(newIntent, getTitle()))
        }
        finish()
    }

}
