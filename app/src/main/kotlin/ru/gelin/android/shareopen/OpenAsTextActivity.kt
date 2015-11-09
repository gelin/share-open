package ru.gelin.android.shareopen

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Opens the shared text.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT.
 *  Puts the text into temporary content provider.
 *  Produces ACTION_VIEW intent with Type equals to "text/plain"
 *  and the Data is the Uri to the text in the content provider.
 */
public class OpenAsTextActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            val values = ContentValues()
            values.put(TextContentProvider.TEXT_COLUMN, text)
            try {
                val uri = contentResolver.insert(TextContentProvider.CONTENT_URI, values)
                val newIntent = Intent(Intent.ACTION_VIEW)
                newIntent.setDataAndType(uri, "text/plain")
                startActivity(Intent.createChooser(newIntent, title))
            } catch (e: Exception) {
                Log.d(TAG, "Cannot open shared text from intent: " + intent, e)
                Toast.makeText(this, R.string.cannot_open_text, Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d(TAG, "No EXTRA_TEXT in the intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_text, Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
