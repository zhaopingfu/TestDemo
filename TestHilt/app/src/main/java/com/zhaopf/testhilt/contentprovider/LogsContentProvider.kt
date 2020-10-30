package com.zhaopf.testhilt.contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.zhaopf.testhilt.data.LogDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import java.lang.UnsupportedOperationException

/**
 * @author zhaopingfu
 * @date 2020/10/30
 */

/** The authority of this content provider.  */
private const val LOGS_TABLE = "logs"

/** The authority of this content provider.  */
private const val AUTHORITY = "com.zhaopf.testhilt.provider"

/** The match code for some items in the Logs table.  */
private const val CODE_LOGS_DIR = 1

/** The match code for an item in the Logs table.  */
private const val CODE_LOGS_ITEM = 2

class LogsContentProvider : ContentProvider() {

    @InstallIn(ApplicationComponent::class)
    @EntryPoint
    interface LogsContentProviderEntryPoint {

        fun logDao(): LogDao
    }

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, LOGS_TABLE, CODE_LOGS_DIR)
        addURI(AUTHORITY, "$LOGS_TABLE/*", CODE_LOGS_ITEM)
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code: Int = matcher.match(uri)
        return if (code == CODE_LOGS_DIR || code == CODE_LOGS_ITEM) {
            val appContext: Context = context?.applicationContext ?: throw IllegalStateException()
            val logDao: LogDao = getLogDao(appContext)

            val cursor: Cursor? = if (code == CODE_LOGS_DIR) {
                logDao.selectAllLogsCursor()
            } else {
                logDao.selectLogById(ContentUris.parseId(uri))
            }
            cursor?.setNotificationUri(appContext.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    private fun getLogDao(appContext: Context): LogDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            LogsContentProviderEntryPoint::class.java
        )
        return hiltEntryPoint.logDao()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }
}