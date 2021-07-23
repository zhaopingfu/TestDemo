package com.zhaopingfu.roomwordsample

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author zhaopingfu
 * @date 2021-07-22 16:47
 */
@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>
}
