package ru.gelin.android.shareopen

import android.content.Intent

/**
 * A set of functions to convert intents.
 */

/**
 *  Converts ACTION_VIEW Intent into ACTION_SEND Intent with EXTRA_TEXT.
 *  The Data URI of the original Intent is put as EXTRA_TEXT of the target intent.
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

