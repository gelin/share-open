package ru.gelin.android.shareopen

import android.content.ContentValues
import android.net.Uri
import android.test.ProviderTestCase2
import java.io.FileNotFoundException

class TextContentProviderTest :
        ProviderTestCase2<TextContentProvider>(TextContentProvider::class.java, TextContentProvider.CONTENT_AUTHORITY) {

    fun testInsertAndOpenStream() {
        val values = ContentValues()
        values.put(TextContentProvider.TEXT_COLUMN, "Hello, World!")
        val uri = mockContentResolver.insert(TextContentProvider.CONTENT_URI, values)
        assertEquals("0a0a9f2a6772942557ab5355d76af442f8f65e01", uri.lastPathSegment)   //SHA1 hash
        val file = mockContentResolver.openInputStream(uri)
        assertEquals("Hello, World!", file.reader().readText())
        file.close()
    }

    fun testGetStreamTypes() {
        val uri = Uri.withAppendedPath(TextContentProvider.CONTENT_URI, "test")
        assertNull(mockContentResolver.getStreamTypes(uri, null))
        assertNull(mockContentResolver.getStreamTypes(uri, "image/*"))
        val types1 = mockContentResolver.getStreamTypes(uri, "*/*")
        assertEquals(1, types1.size)
        assertEquals("text/plain", types1[0])
        val types2 = mockContentResolver.getStreamTypes(uri, "text/*")
        assertEquals(1, types2.size)
        assertEquals("text/plain", types2[0])
    }

    fun testOpenStreamNotExist() {
        val uri = Uri.withAppendedPath(TextContentProvider.CONTENT_URI, "not existing")
        try {
            mockContentResolver.openInputStream(uri)
            fail("No exception occurred")
        } catch (fnfe: FileNotFoundException) {
            return      // pass
        }
        fail("Not FileNotFoundException occurred")
    }

    fun testDelete() {
        val values = ContentValues()
        values.put(TextContentProvider.TEXT_COLUMN, "Hello, World!")
        val uri = mockContentResolver.insert(TextContentProvider.CONTENT_URI, values)
        mockContentResolver.openInputStream(uri)    // success
        val deleted = mockContentResolver.delete(uri, "", arrayOf())
        assertEquals(1, deleted)
        try {
            mockContentResolver.openInputStream(uri)
            fail("No exception occurred")
        } catch (fnfe: FileNotFoundException) {
            return      // pass
        }
        fail("Not FileNotFoundException occurred")
    }

}