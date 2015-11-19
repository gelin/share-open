package ru.gelin.android.shareopen

import android.content.ContentValues
import android.test.ProviderTestCase2

class TextContentProviderTest :
        ProviderTestCase2<TextContentProvider>(TextContentProvider::class.java, TextContentProvider.CONTENT_AUTHORITY) {

    fun testInsertAndQuery() {
        val values = ContentValues()
        values.put(TextContentProvider.TEXT_COLUMN, "Hello, World!")
        val uri = mockContentResolver.insert(TextContentProvider.CONTENT_URI, values)
        assertEquals("0a0a9f2a6772942557ab5355d76af442f8f65e01", uri.lastPathSegment)   //SHA1 hash
        val cursor = mockContentResolver.query(uri, arrayOf(TextContentProvider.TEXT_COLUMN), "", arrayOf(), "")
        assertTrue(cursor.moveToNext())
        assertEquals(1, cursor.columnCount)
        assertEquals(0, cursor.getColumnIndex(TextContentProvider.TEXT_COLUMN))
        assertEquals("Hello, World!", cursor.getString(0))
        cursor.close()
    }

}