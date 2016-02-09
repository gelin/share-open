package ru.gelin.android.shareopen

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.test.AndroidTestCase

class ViewIntentResolveTest : AndroidTestCase() {

    fun testViewHttp() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
    }

    fun testViewHttps() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
    }

    fun testViewFtp() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("ftp://example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
    }

    fun testViewMailto() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:test@example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
    }

    fun testViewUrn() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("urn:test"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
    }

    fun testViewHttpHtml() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("http://example.com"), "text/html")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewFtpZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("ftp://example.com"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewFileZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file:///tmp/test.zip"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewFileText() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file:///tmp/test.txt"), "text/plain")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewHttpText() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("http://example.com"), "text/plain")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewContentZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("content://authority/path/id"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewContentText() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("content://authority/path/id"), "text/plain")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
    }

    fun testViewHttpsPdf() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("https://example.com/file.pdf"), "application/pdf")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertTrue("missed ShareAsLinkActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsLinkActivity" })
        assertTrue("missed ShareAsFileActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.ShareAsFileActivity" })
//        assertTrue(activities.size > 2)
    }

}
