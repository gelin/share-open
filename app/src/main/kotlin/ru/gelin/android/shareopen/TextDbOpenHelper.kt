package ru.gelin.android.shareopen

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/** Version of database schema  */
private const val DATABASE_VERSION = 1

/** Text table name  */
internal const val TEXT_TABLE = "text"

/** ID column name  */
internal const val ID_COLUMN = "id"
/** Timestamp column name  */
internal const val TIMESTAMP_COLUMN = "timestamp"
/** Text column name  */
internal const val TEXT_COLUMN = "text"

/**
 *  A SQLite database to store texts.
 */
internal class TextDbOpenHelper(context: Context, name: String): SQLiteOpenHelper(context, name, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE $TEXT_TABLE (
            $ID_COLUMN TEXT PRIMARY KEY,
            $TIMESTAMP_COLUMN INTEGER,
            $TEXT_COLUMN TEXT
            )""")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // do nothing, we have only one version for now
    }

}