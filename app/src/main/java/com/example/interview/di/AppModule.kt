package com.example.interview.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.interview.api.APIInterface
import com.example.interview.database.AppDatabase
import com.example.interview.database.UserDao
import com.example.interview.database.UserRepository
import com.example.interview.utilites.BASE_URL
import com.example.interview.utilites.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): APIInterface{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(dao: UserDao): UserRepository{
        return UserRepository(dao)
    }


    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideYourDatabase(
        app: Application) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()




}