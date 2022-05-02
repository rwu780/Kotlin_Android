package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntity>)

    @Query("DELETE FROM wordinfoentity where word IN (:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%android%'")
    suspend fun getWordInfos(): List<WordInfoEntity>?

}