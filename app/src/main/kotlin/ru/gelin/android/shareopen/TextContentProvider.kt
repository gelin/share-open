package ru.gelin.android.shareopen

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import ru.gelin.kotlin.util.hex.toHexString
import java.security.MessageDigest


private const val URI_BASE = 1
private const val URI_WITH_ID = 2

private const val CONTENT_AUTHORITY = "ru.gelin.android.shareopen.provider"
private const val CONTENT_TEXT_PATH = "text"

private val URI_MATCHER = object : UriMatcher(UriMatcher.NO_MATCH) {
    init {
        addURI(CONTENT_AUTHORITY, "$CONTENT_TEXT_PATH", URI_BASE)
        addURI(CONTENT_AUTHORITY, "$CONTENT_TEXT_PATH/*", URI_WITH_ID)
    }
}

private const val DB_NAME = "texts"
private const val TTL = "-2 days"

class TextContentProvider : ContentProvider() {

    companion object {

        /** Base content URI for the text content provider */
        val CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY/$CONTENT_TEXT_PATH")

        /** Text column name  */
        const val TEXT_COLUMN = "text"

    }

    lateinit private var dbHelper : SQLiteOpenHelper

    override fun onCreate(): Boolean {
        this.dbHelper = TextDbOpenHelper(context, DB_NAME)
        return false
    }

    override fun getType(uri: Uri): String {
        return when (URI_MATCHER.match(uri)) {
            URI_BASE -> "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TEXT_TABLE"
            URI_WITH_ID -> "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TEXT_TABLE"
            else -> throw IllegalArgumentException("Access to $uri is not supported")
        }
    }

    override fun getStreamTypes(uri: Uri?, mimeTypeFilter: String?): Array<out String>? {
        return when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> if (mimeTypeFilter?.startsWith("text/") ?: false) arrayOf("text/plain") else null
            else -> throw IllegalArgumentException("Access to $uri is not supported")
        }
    }

    override fun query(uri: Uri, projection: Array<String>, selection: String,
                       selectionArgs: Array<String>, sortOrder: String): Cursor {
        when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> {
                val db = this.dbHelper.readableDatabase
                val id = uri.lastPathSegment
                return db.query(TEXT_TABLE, arrayOf(TEXT_COLUMN), "$ID_COLUMN = ?", arrayOf(id), null, null, null, null)
            }
            else -> throw IllegalArgumentException("Querying of $uri is not supported")
        }
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        when (URI_MATCHER.match(uri)) {
            URI_BASE -> {
                val db = this.dbHelper.writableDatabase
                db.beginTransaction()
                try {
                    db.delete(TEXT_TABLE, "$TIMESTAMP_COLUMN < date('now', '$TTL')", null)
                    //context.revokeUriPermission() ???
                    val text = values.getAsString(TEXT_COLUMN)
                    val id = getId(text)
                    val insValues = ContentValues()
                    insValues.put(ID_COLUMN, id)
                    insValues.put(TIMESTAMP_COLUMN, System.currentTimeMillis() / 1000)
                    insValues.put(TEXT_COLUMN, text)
                    db.insert(TEXT_TABLE, null, insValues)
                    db.setTransactionSuccessful()
                    return Uri.withAppendedPath(uri, id)
                } finally {
                    db.endTransaction()
                }
            }
            else -> throw IllegalArgumentException("Inserting to $uri is not supported")
        }
    }

    private fun getId(text: String): String {
        val digester = MessageDigest.getInstance("SHA1")
        val bytes = text.toByteArray()
        val digest = digester.digest(bytes)
        return digest.toHexString()
    }

    override fun delete(uri: Uri, selection: String, selectionArgs: Array<String>): Int {
        when (URI_MATCHER.match(uri)) {
            URI_WITH_ID -> {
                val db = this.dbHelper.writableDatabase
                val id = uri.lastPathSegment
                return db.delete(TEXT_TABLE, "id = ?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("Deleting of $uri is not supported")
        }
    }

    override fun update(uri: Uri, values: ContentValues, selection: String,
                        selectionArgs: Array<String>): Int {
        throw IllegalArgumentException("Updating of $uri is not supported")
    }

}
