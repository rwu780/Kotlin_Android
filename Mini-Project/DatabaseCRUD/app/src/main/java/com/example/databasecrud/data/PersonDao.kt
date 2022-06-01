package com.example.databasecrud.data

import androidx.room.*
import com.example.databasecrud.data.model.Person
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Query("DELETE FROM person WHERE email =:email")
    suspend fun deletePerson(email: String)

    @Query("SELECT * FROM person")
    fun selectAll() : Flow<List<Person>>

    @Query("SELECT * FROM person WHERE email =:email")
    suspend fun selectPerson(email: String) : Person
}