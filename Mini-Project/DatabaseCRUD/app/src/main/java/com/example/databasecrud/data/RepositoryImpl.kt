package com.example.databasecrud.data

import com.example.databasecrud.data.model.Person
import com.example.databasecrud.domain.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val personDao: PersonDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun insertPerson(person: Person) = withContext(ioDispatcher){
        personDao.insertPerson(person)
    }

    override suspend fun updatePerson(person: Person) = withContext(ioDispatcher) {
        personDao.updatePerson(person)
    }

    override fun getAll(): Flow<List<Person>> {
        return personDao.selectAll()
    }

    override suspend fun getPerson(email: String): Person = withContext(ioDispatcher) {
        return@withContext personDao.selectPerson(email)
    }

    override suspend fun deletePerson(person: Person) = withContext(ioDispatcher){
        personDao.deletePerson(person)
    }

    override suspend fun deletePerson(email: String) = withContext(ioDispatcher){
        personDao.deletePerson(email)
    }
}