package ru.gelin.android.shareopen

import android.content.Intent
import android.content.pm.PackageManager
import android.test.AndroidTestCase

class SendIntentResolveTest : AndroidTestCase() {

    fun testSendTextLink() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "http://example.com")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed OpenAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsLinkActivity" })
//        assertTrue("missed OpenAsFileActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsFileActivity" })
    }

//    fun testSendFile() {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.setType("application/zip")
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///tmp/test.zip"))
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertFalse("unnecessary OpenAsLinkActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsLinkActivity" })
//        assertTrue("missed OpenAsFileActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsFileActivity" })
//    }

//    fun testSendTextFileWithLink() {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.setType("text/plain")
//        val values = ContentValues()
//        values.put(TextContentProvider.TEXT_COLUMN, "http://example.com")
//        val textUri = context.contentResolver.insert(TextContentProvider.CONTENT_URI, values)
//        intent.putExtra(Intent.EXTRA_STREAM, textUri)
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed OpenAsLinkActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsLinkActivity" })
//        assertTrue("missed OpenAsFileActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.OpenAsFileActivity" })
//    }

}
