package com.example.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.feature_dictionary.data.WordInfoRepositoryImpl
import com.example.dictionaryapp.feature_dictionary.data.local.Converter
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.data.util.GsonParser
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dictionary.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Appendable
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(respository: WordInfoRepository) : GetWordInfo {
        return GetWordInfo(respository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryApi

        ) : WordInfoRepository {
        return WordInfoRepositoryImpl(api = api, dao = db.wordInfoDao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        )
            .addTypeConverter(Converter(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder().baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }


}