package com.yechy.dailypic.di

import android.app.Application
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
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
object ApplicationModule {

    @Provides
    @Singleton
    fun provideDataRepos(dbRepos: DbRepos, httpRepos: HttpRepos): DataRepos {
        return DataRepos(dbRepos, httpRepos)
    }

    @Provides
    @Singleton
    fun provideDailyPicApplication(application: Application): DailyPicApp = application as DailyPicApp
}