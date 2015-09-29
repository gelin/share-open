package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle

public class OpenFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_STREAM)) {
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.setDataAndType(intent.getParcelableExtra(Intent.EXTRA_STREAM), intent.type ?: "*/*")
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

}
