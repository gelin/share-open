package ru.gelin.android.shareopen

import android.content.ContentValues
import android.test.ProviderTestCase2

class TextContentProviderTest :
        ProviderTestCase2<TextContentProvider>(TextContentProvider::class.java, TextContentProvider.CONTENT_AUTHORITY) {

    fun testInsertAndOpenStream() {
        val values = ContentValues()
        values.put(TextContentProvider.TEXT_COLUMN, "Hello, World!")
        val uri = mockContentResolver.insert(TextContentProvider.CONTENT_URI, values)
        assertEquals("0a0a9f2a6772942557ab5355d76af442f8f65e01", uri.lastPathSegment)   //SHA1 hash
        val types = mockContentResolver.getStreamTypes(uri, "*/*")
        assertEquals(1, types.size)
        assertEquals("text/plain", types[0])
        val file = mockContentResolver.openInputStream(uri)
        assertEquals("Hello, World!", file.reader().readText())
        file.close()
    }

}