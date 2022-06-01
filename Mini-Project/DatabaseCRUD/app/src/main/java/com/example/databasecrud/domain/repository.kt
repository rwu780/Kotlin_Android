package com.example.databasecrud.domain

import com.example.databasecrud.data.model.Person
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertPerson(person: Person)
    suspend fun updatePerson(person: Person)
    fun getAll() : Flow<List<Person>>
    suspend fun getPerson(email: String) : Person
    suspend fun deletePerson(person: Person)
    suspend fun deletePerson(email: String)
}