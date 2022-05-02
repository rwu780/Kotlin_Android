package com.example.dictionaryapp.feature_dictionary.presentation

import android.provider.Contacts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _state = MutableLiveData(WordInfoSate())
    val state: LiveData<WordInfoSate> get() = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null


    fun onSearch(query: String) {

        _searchQuery.value = query
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500L)

            getWordInfo(query).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = state.value?.copy(
                            wordInfoItem = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value?.copy(
                            wordInfoItem = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value?.copy(
                            wordInfoItem = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(result.message ?: "Unknown Error"))
                    }
                }
            }.launchIn(this)

        }

    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()


    }
}