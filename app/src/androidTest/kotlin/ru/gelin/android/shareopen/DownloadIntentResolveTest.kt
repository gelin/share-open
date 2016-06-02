package ru.gelin.android.shareopen

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.test.AndroidTestCase

class DownloadIntentResolveTest : AndroidTestCase() {

//    fun testViewHttp() {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://example.com"))
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed DownloadActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
//    }

//    fun testViewHttps() {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"))
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed DownloadActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
//    }

    fun testViewFtp() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("ftp://example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

    fun testViewMailto() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:test@example.com"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

    fun testViewUrn() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("urn:test"))
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

//    fun testViewHttpHtml() {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse("http://example.com"), "text/html")
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed DownloadActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
//    }

    fun testViewFtpZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("ftp://example.com"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

    fun testViewFileZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file:///tmp/test.zip"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

    fun testViewFileText() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file:///tmp/test.txt"), "text/plain")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

//    fun testViewHttpText() {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse("http://example.com"), "text/plain")
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed DownloadActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
//    }

    fun testViewContentZip() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("content://authority/path/id"), "application/zip")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

    fun testViewContentText() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("content://authority/path/id"), "text/plain")
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        assertFalse("unnecessary DownloadActivity",
                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
    }

//    fun testViewHttpsPdf() {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse("https://example.com/file.pdf"), "application/pdf")
//        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        assertTrue("missed DownloadActivity",
//                activities.any { info -> info.activityInfo.name == "ru.gelin.android.shareopen.DownloadActivity" })
//    }

}
