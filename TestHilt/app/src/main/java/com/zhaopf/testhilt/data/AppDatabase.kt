package com.zhaopf.testhilt.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
@Database(entities = arrayOf(Log::class), version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao
}