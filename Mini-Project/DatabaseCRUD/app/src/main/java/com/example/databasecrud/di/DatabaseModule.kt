package com.example.databasecrud.di

import android.content.Context
import androidx.room.Room
import com.example.databasecrud.data.PersonDB
import com.example.databasecrud.data.PersonDao
import com.example.databasecrud.data.RepositoryImpl
import com.example.databasecrud.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePersonDatabase(@ApplicationContext cxt: Context): PersonDB {
        return Room.databaseBuilder(
            cxt,
            PersonDB::class.java,
            PersonDB.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providePersonDao(personDb: PersonDB): PersonDao {
        return personDb.getPersonDao()
    }

    @Singleton
    @Provides
    fun provideRepository(personDao: PersonDao): Repository {
        return RepositoryImpl(personDao)
    }
}