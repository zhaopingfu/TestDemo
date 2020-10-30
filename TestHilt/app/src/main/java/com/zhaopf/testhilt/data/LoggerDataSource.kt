package com.zhaopf.testhilt.data

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
interface LoggerDataSource {

    fun addLog(msg: String)

    fun getAllLogs(callback: (List<Log>) -> Unit)

    fun removeLogs()
}
