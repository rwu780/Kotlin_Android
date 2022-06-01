package com.example.databasecrud.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(

    @PrimaryKey
    val email: String,
    val firstName: String,
    val lastName: String,


)
