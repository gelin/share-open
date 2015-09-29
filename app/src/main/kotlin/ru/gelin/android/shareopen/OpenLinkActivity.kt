package ru.gelin.android.shareopen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import kotlin.text.Regex

public class OpenLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Intent.ACTION_SEND == intent.action && intent.hasExtra(Intent.EXTRA_TEXT)) {
            val link = findLinkInText(intent.getStringExtra(Intent.EXTRA_TEXT))
            if (null == link) {
                Toast.makeText(this, R.string.no_link_found, Toast.LENGTH_LONG).show()
                finish()
                return
            }
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.setDataAndType(link, intent.type ?: "*/*")
            startActivity(Intent.createChooser(newIntent, title))
        }
        finish()
    }

    //http://blog.mattheworiordan.com/post/13174566389/url-regular-expression-for-links-with-or-without
    val LINK_REGEX = Regex("""((([A-Za-z]{3,9}:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)""")

    fun findLinkInText(text: String): Uri? {
        val link = LINK_REGEX.match(text)?.value ?: return null
        return Uri.parse(link)
    }

}
