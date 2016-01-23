package ru.gelin.android.shareopen

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import ru.gelin.kotlin.util.hex.toHexString
import java.io.File
import java.security.MessageDigest
import java.util.*


class TextContentProvider : ContentProvider() {

    companion object {

        /** This content provider authority */
        const val CONTENT_AUTHORITY = "ru.gelin.android.shareopen.provider"

        private const val CONTENT_TEXT_PATH = "text"

        /** Base content URI for the text content provider */
        val CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY/$CONTENT_TEXT_PATH")

        /** Text column name  */
        const val TEXT_COLUMN = "text"

        private const val URI_BASE = 1
        private const val URI_WITH_ID = 2

        private val URI_MATCHER = object : UriMatcher(UriMatcher.NO_MATCH) {
            init {
                addURI(CONTENT_AUTHORITY, "$CONTENT_TEXT_PATH", URI_BASE)
                addURI(CONTENT_AUTHORITY, "$CONTENT_TEXT_PATH/*", URI_WITH_ID)
            }
        }

        private const val TTL = 2 * 24 * 60 * 60 * 1000L    // 2 days

    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String {
        throw IllegalArgumentException("Table access to $uri is not supported")
    }

    override fun getStreamTypes(uri: Uri?, mimeTypeFilter: String?): Array<out String>? {
        return when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> when {
                mimeTypeFilter == null -> null
                mimeTypeFilter.startsWith("text/") || mimeTypeFilter.equals("*/*") -> arrayOf("text/plain")
                else -> null
            }
            else -> throw IllegalArgumentException("Stream access to $uri is not supported")
        }
    }

    override fun query(uri: Uri, projection: Array<String>, selection: String,
                       selectionArgs: Array<String>, sortOrder: String): Cursor {
        throw IllegalArgumentException("Querying of $uri is not supported")
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        when (URI_MATCHER.match(uri)) {
            URI_BASE -> {
                deleteOldFiles()
                //context.revokeUriPermission() ???
                val text = values.getAsString(TEXT_COLUMN)
                val id = getId(text)
                createNewFile(id, text)
                return Uri.withAppendedPath(uri, id)
            }
            else -> throw IllegalArgumentException("Inserting to $uri is not supported")
        }
    }

    private fun deleteOldFiles() {
        val oldLine = System.currentTimeMillis() - TTL
        for (file in context.cacheDir.listFiles()) {
            if (file.lastModified() < oldLine) {
                file.delete()
            }
        }
    }

    private fun getId(text: String): String {
        val digester = MessageDigest.getInstance("SHA1")
        val bytes = text.toByteArray()
        val digest = digester.digest(bytes)
        return digest.toHexString()
    }

    private fun getFile(fileName: String): File {
        return File(context.cacheDir, fileName)
    }

    private fun createNewFile(fileName: String, content: String) {
        getFile(fileName).writer().use {
            it.write(content)
        }
    }

    override fun delete(uri: Uri, selection: String, selectionArgs: Array<String>): Int {
        when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> {
                val id = uri.lastPathSegment
                return if (deleteFile(id)) 1 else 0
            }
            else -> throw IllegalArgumentException("Deleting of $uri is not supported")
        }
    }

    private fun deleteFile(fileName: String): Boolean {
        return getFile(fileName).delete()
    }

    override fun update(uri: Uri, values: ContentValues, selection: String,
                        selectionArgs: Array<String>): Int {
        throw IllegalArgumentException("Updating of $uri is not supported")
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        if (mode.contains('w')) {
            throw SecurityException("Cannot write $uri. Read-only provider.")
        }
        when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> {
                val id = uri.lastPathSegment
                return openFile(id)
            }
            else -> throw IllegalArgumentException("Cannot open $uri")
        }
    }

    private fun openFile(fileName: String): ParcelFileDescriptor {
        return ParcelFileDescriptor.open(getFile(fileName), ParcelFileDescriptor.MODE_READ_ONLY)
    }

}
