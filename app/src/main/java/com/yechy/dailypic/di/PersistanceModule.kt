package com.yechy.dailypic.di

import android.app.Application
import androidx.room.Room
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.db.AppDatabase
import com.yechy.dailypic.repository.db.DbRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Module
@InstallIn(SingletonComponent::class)
object PersistanceModule {

    const val DATABASE_NAME = "dailypic_db"

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDbRepos(appDatabase: AppDatabase): DbRepos = DbRepos(appDatabase)
}