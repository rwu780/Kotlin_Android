package com.example.noteapp.feature_note.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableLiveData<NotesState>(NotesState())
    val state :LiveData<NotesState> = _state

    private var recentDeleteNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event) {
            is NotesEvent.Order -> {
                if (state.value!!.noteOrder::class == event.noteOrder::class &&
                        state.value!!.noteOrder.orderType == event.noteOrder.orderType)
                            return

                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNode(event.note)
                    recentDeleteNote = event.note

                }

            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentDeleteNote ?: return@launch)
                    recentDeleteNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value?.copy(
                    isOrderSectionVisible = !(state.value?.isOrderSectionVisible)!!
                )

            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = noteUseCases.getNodes(noteOrder)
            .onEach { notes ->
                _state.value = state.value?.copy(notes = notes, noteOrder = noteOrder)
            }
            .launchIn(viewModelScope)

    }


}