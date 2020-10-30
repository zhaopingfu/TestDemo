package com.zhaopf.testhilt.data

import java.util.*
import javax.inject.Inject

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
class LoggerInMemoryDataSource @Inject constructor() : LoggerDataSource {

    private val logs by lazy { LinkedList<Log>() }

    override fun addLog(msg: String) {
        logs.add(Log(msg, System.currentTimeMillis()))
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }

    override fun removeLogs() {
        logs.clear()
    }
}
