package com.zhaopf.testhilt.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
@Dao
interface LogDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun getAll(): List<Log>

    @Insert
    fun insertAll(vararg logs: Log)

    @Query("DELETE FROM logs")
    fun nukeTable()

    @Query("SELECT * FROM logs ORDER BY id ASC")
    fun selectAllLogsCursor(): Cursor

    @Query("SELECT * FROM logs WHERE id = :id")
    fun selectLogById(id: Long): Cursor?
}
