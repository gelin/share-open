package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle

public class OpenFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()
        if (Intent.ACTION_SEND == intent.getAction() && intent.hasExtra(Intent.EXTRA_STREAM)) {
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.setDataAndType(intent.getParcelableExtra(Intent.EXTRA_STREAM), intent.getType() ?: "*/*")
            startActivity(Intent.createChooser(newIntent, getTitle()))
        }
        finish()
    }

}
