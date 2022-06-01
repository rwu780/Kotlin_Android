package com.example.databasecrud.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databasecrud.data.model.Person

@Database(
    entities = [Person::class],
    version = 1
)
abstract class PersonDB : RoomDatabase(){

    abstract fun getPersonDao() : PersonDao

    companion object {
        val DATABASE_NAME = "person_database"
    }

}