package ru.gelin.android.shareopen

import android.content.Intent
import android.net.Uri
import kotlin.text.Regex

/**
 * A set of functions to convert intents.
 */

/**
 *  Converts ACTION_VIEW Intent into ACTION_SEND Intent with EXTRA_TEXT.
 *  The Data URI of the original Intent is put as EXTRA_TEXT of the target Intent.
 *  @return the converted Intent or null if the conversion is not possible
 */
fun viewToSendText(intent: Intent): Intent? {
    if (Intent.ACTION_VIEW != intent.action) {
        return null
    }
    if (null == intent.data) {
        return null
    }
    val newIntent = Intent(Intent.ACTION_SEND)
    newIntent.setType("text/plain")
    newIntent.putExtra(Intent.EXTRA_TEXT, intent.dataString)
    return newIntent
}

/**
 *  Converts ACTION_VIEW Intent into ACTION_SEND Intent with EXTRA_STREAM.
 *  The Data URI of the original Intent is put as EXTRA_STREAM of the target Intent.
 *  @return the converted Intent or null if the conversion is not possible
 */
fun viewToSendStream(intent: Intent): Intent? {
    if (Intent.ACTION_VIEW != intent.action) {
        return null
    }
    if (null == intent.data) {
        return null
    }
    val newIntent = Intent(Intent.ACTION_SEND)
    newIntent.setType(intent.type ?: "*/*")
    newIntent.putExtra(Intent.EXTRA_STREAM, intent.data)
    return newIntent
}

//http://blog.mattheworiordan.com/post/13174566389/url-regular-expression-for-links-with-or-without
val LINK_REGEX = Regex("""((([A-Za-z]{3,9}:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)""")

fun findLinkInText(text: String): Uri? {
    val link = LINK_REGEX.find(text)?.value ?: return null
    return Uri.parse(link)
}

/**
 *  Converts ACTION_SEND Intent with EXTRA_TEXT into ACTION_VIEW Intent.
 *  Takes the EXTRA_TEXT, tries to find the first URI in it,
 *  puts the found URI as Data of the target Intent.
 *  @return the converted Intent or null if the conversion is not possible
 */
fun sendTextToViewLink(intent: Intent): Intent? {
    if (Intent.ACTION_SEND != intent.action || !intent.hasExtra(Intent.EXTRA_TEXT)) {
        return null
    }
    val text = intent.getStringExtra(Intent.EXTRA_TEXT)
    val link = findLinkInText(text) ?: return null
    return Intent(Intent.ACTION_VIEW, link)
}

/**
 *  Converts ACTION_SEND Intent with EXTRA_STREAM into ACTION_VIEW Intent.
 *  The EXTRA_STREAM is set as the target Intent Data,
 *  the Type of the original Intent is copied to Type of the target Intent.
 *  @return the converted Intent or null if the conversion is not possible
 */
fun sendStreamToViewFile(intent: Intent): Intent? {
    if (Intent.ACTION_SEND != intent.action || !intent.hasExtra(Intent.EXTRA_STREAM)) {
        return null
    }
    val newIntent = Intent(Intent.ACTION_VIEW)
    newIntent.setDataAndType(intent.getParcelableExtra(Intent.EXTRA_STREAM), intent.type)
    return newIntent
}
