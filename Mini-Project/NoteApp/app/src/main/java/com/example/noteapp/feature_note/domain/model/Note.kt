package com.example.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.R
import java.lang.Exception

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    companion object {
        val noteColor = listOf(
            R.color.red_orange,
            R.color.light_green,
            R.color.violet,
            R.color.baby_blue,
            R.color.red_pink)
    }
}

class InvalidNoteException(message: String): Exception(message)