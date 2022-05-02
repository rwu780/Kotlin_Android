package com.example.dictionaryapp.feature_dictionary.data

import android.util.Log
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "WordInfoRepositoryImpl"
class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        Log.d(TAG, "getWordInfo: ")
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos()?.let { wordList ->
            wordList.map { wordEntity ->
                wordEntity.toWordInfo()
            }
        } ?: emptyList()

        emit(Resource.Loading(wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)

            Log.d(TAG, "getWordInfo: $remoteWordInfos")
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch ( e: HttpException ) {
            emit(Resource.Error(message = "Ooops, something went wrong!", data = wordInfos))

        } catch (  e: IOException ) {
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection.", data = wordInfos))

        }

        val newWordsInfo = dao.getWordInfos()?.let { wordEntityList ->
            wordEntityList.map { it.toWordInfo() }
        } ?: emptyList()
        emit(Resource.Success(newWordsInfo))

    }
}