package com.zhaopf.testhilt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
@Entity(tableName = "logs")
data class Log(val msg: String, val timestamp: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}