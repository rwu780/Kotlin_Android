package com.example.dictionaryapp.feature_dictionary.domain.use_case

import android.util.Log
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "GetWordInfo"
class GetWordInfo(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        Log.d(TAG, "invoke: $word")
        if (word.isBlank()) {
            return flow { }
        }

        return repository.getWordInfo(word)
    }
}