package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 *  Opens the shared link.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT.
 *  Searched the link in the shared text.
 *  Produces ACTION_VIEW intent with DataAndType taken from the input intent, the Data is the found link.
 */
public class OpenAsTextActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            //TODO: save text to a content provider, return intent with uri to the saved text
//            val link = findLinkInText(text)
//            if (null != link) {
//                val newIntent = Intent(Intent.ACTION_VIEW)
//                newIntent.setDataAndType(link, intent.type ?: "*/*")
//                startActivity(Intent.createChooser(newIntent, title))
//            } else {
//                Log.d(TAG, "No links found in the shared text: " + text)
//                Toast.makeText(this, R.string.no_link_found, Toast.LENGTH_LONG).show()
//            }
        } else {
            Log.d(TAG, "Cannot open shared link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_link, Toast.LENGTH_LONG).show()
        }
        finish()
    }

}
