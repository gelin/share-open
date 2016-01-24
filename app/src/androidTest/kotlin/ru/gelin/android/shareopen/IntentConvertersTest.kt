package ru.gelin.android.shareopen

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.test.AndroidTestCase

class IntentConvertersTest : AndroidTestCase() {

    fun testViewToSendText() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://example.com"))
        val newIntent = viewToSendText(intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_SEND, newIntent?.action)
        assertEquals("text/plain", newIntent?.type)
        assertEquals("http://example.com", newIntent?.getStringExtra(Intent.EXTRA_TEXT))
    }

    fun testViewToSendStream() {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse("http://example.com")
        intent.setDataAndType(uri, "text/html")
        val newIntent = viewToSendStream(intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_SEND, newIntent?.action)
        assertEquals("text/html", newIntent?.type)
        assertEquals(uri, newIntent!!.getParcelableExtra(Intent.EXTRA_STREAM))
    }

    fun testSendTextToViewLink() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, "http://example.com")
        val newIntent = sendTextToViewLink(intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_VIEW, newIntent?.action)
        assertNull(newIntent?.type)
        assertEquals(Uri.parse("http://example.com"), newIntent?.data)
    }

    fun testSendStreamToViewFile() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/html")
        val uri = Uri.parse("http://example.com")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        val newIntent = sendStreamToViewFile(intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_VIEW, newIntent?.action)
        assertEquals("text/html", newIntent?.type)
        assertEquals(uri, newIntent?.data)
    }

    fun testSendTextToViewFile() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, "Hello, World!")
        val newIntent = sendTextToViewFile(context, intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_VIEW, newIntent?.action)
        assertEquals("text/plain", newIntent?.type)
        assertEquals(Uri.parse("content://ru.gelin.android.shareopen.provider/text/0a0a9f2a6772942557ab5355d76af442f8f65e01"), newIntent?.data)
    }

    fun testSendStreamToViewLink() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        val values = ContentValues()
        values.put(TextContentProvider.TEXT_COLUMN, "http://example.com")
        val textUri = context.contentResolver.insert(TextContentProvider.CONTENT_URI, values)
        intent.putExtra(Intent.EXTRA_STREAM, textUri)
        val newIntent = sendStreamToViewLink(context, intent)
        assertNotNull(newIntent)
        assertEquals(Intent.ACTION_VIEW, newIntent?.action)
        assertNull(newIntent?.type)
        assertEquals(Uri.parse("http://example.com"), newIntent?.data)
    }

}
