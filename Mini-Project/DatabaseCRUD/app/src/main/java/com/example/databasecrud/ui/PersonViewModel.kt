package com.example.databasecrud.ui

import androidx.lifecycle.*

import com.example.databasecrud.data.model.Person
import com.example.databasecrud.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private fun insertPerson(person: Person) {
        viewModelScope.launch {
            repository.insertPerson(person)
        }
    }

    fun insertPerson(firstName: String, lastName: String, email: String) {
        insertPerson(
            Person(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        )
    }

    fun retrievePerson(email: String) = liveData<Person> {
        if (email.isNotBlank())
            emit(repository.getPerson(email))
    }

    fun retrieveAllPerson() : Flow<List<Person>> = repository.getAll()


    fun deleteUser(email: String){
        viewModelScope.launch {
            repository.deletePerson(email)
        }
    }

}