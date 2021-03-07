package com.yechy.dailypic.di

import com.yechy.dailypic.repository.http.BingService
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.support.BingConverterFactory
import com.yechy.dailypic.util.BING_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

/**
 *
 * Created by cloud on 2019-11-06.
 */
@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    @Provides
    @Singleton
    fun createOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        val httpLoggingInteractor = HttpLoggingInterceptor()
        httpLoggingInteractor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.addInterceptor(httpLoggingInteractor)
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BING_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .addConverterFactory(BingConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun createBingService(retrofit: Retrofit): BingService =
        retrofit.create(BingService::class.java)

    @Provides
    @Singleton
    fun createHttpRepos(bingService: BingService): HttpRepos = HttpRepos(bingService)

}