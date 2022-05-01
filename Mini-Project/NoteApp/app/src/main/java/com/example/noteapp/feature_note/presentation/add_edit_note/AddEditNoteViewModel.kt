package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.AddNoteUseCase
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases
) : ViewModel() {

    private val _noteTitle = MutableLiveData<String>()
    val noteTitle: LiveData<String> get() = _noteTitle

    private val _noteContent = MutableLiveData<String>()
    val noteContent: LiveData<String> get() = _noteContent

    private val _noteColor = MutableLiveData<Int>(Note.noteColor.random())
    val noteColor : LiveData<Int> = _noteColor






}