package com.zhaopingfu.roomwordsample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author zhaopingfu
 * @date 2021-07-22 17:02
 */
class WordRepository(private val dao: WordDao) {

    val allWords: Flow<List<Word>> = dao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        dao.insert(word)
    }
}
