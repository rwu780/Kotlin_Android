package com.example.noteapp.feature_note.data.respository

import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.respository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Actual implementation for the repository
 *
 * Since this only read/write data to database, therefore implmentation is simple
 * However, if the app need to both read from database and API, then this repository should decide
 * - when to read from database, and when to read from API calls
 *
 */
class NoteRepositoryImpl(
     private val dao: NoteDao

) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}