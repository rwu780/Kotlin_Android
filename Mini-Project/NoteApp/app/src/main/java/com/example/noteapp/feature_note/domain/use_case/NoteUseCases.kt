package com.example.noteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNodes: GetNotesUseCase,
    val deleteNode: DeleteNodeUseCase,
    val addNote: AddNoteUseCase
)
