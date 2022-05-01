package com.example.noteapp.feature_note.domain.respository

import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * This defines the repository interface. Domain layer do not care where the data comes from
 */
interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}