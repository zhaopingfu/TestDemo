package com.zhaopingfu.roomwordsample

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 *
 * @author zhaopingfu
 * @date 2021-07-22 18:13
 */
class WordsApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }

    val repository by lazy { WordRepository(database.wordDao()) }
}
