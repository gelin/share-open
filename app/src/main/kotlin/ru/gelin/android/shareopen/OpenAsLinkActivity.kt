package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlin.text.Regex

/**
 *  Opens the shared link.
 *  Expects the incoming intent to be ACTION_SEND and has EXTRA_TEXT.
 *  Searched the link in the shared text.
 *  Produces ACTION_VIEW intent with Type taken from the input intent, the Data is the found link.
 */
public class OpenAsLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            val link = findLinkInText(text)
            if (null != link) {
                val newIntent = Intent(Intent.ACTION_VIEW)
                newIntent.setDataAndType(link, intent.type ?: "*/*")
                startActivity(Intent.createChooser(newIntent, title))
            } else {
                Log.d(TAG, "No links found in the shared text: " + text)
                Toast.makeText(this, R.string.no_link_found, Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d(TAG, "Cannot open shared link from intent: " + intent)
            Toast.makeText(this, R.string.cannot_open_link, Toast.LENGTH_LONG).show()
        }
        finish()
    }

    //http://blog.mattheworiordan.com/post/13174566389/url-regular-expression-for-links-with-or-without
    val LINK_REGEX = Regex("""((([A-Za-z]{3,9}:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)""")

    fun findLinkInText(text: String): Uri? {
        val link = LINK_REGEX.find(text)?.value ?: return null
        return Uri.parse(link)
    }

}
