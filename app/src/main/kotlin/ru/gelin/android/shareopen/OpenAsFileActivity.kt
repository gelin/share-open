package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

public class OpenAsFileActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_STREAM)) {
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.setDataAndType(intent.getParcelableExtra(Intent.EXTRA_STREAM), intent.type ?: "*/*")
            startActivity(Intent.createChooser(newIntent, title))
        } else {
            Log.d(TAG, "Cannot open shared file from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_file, Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
