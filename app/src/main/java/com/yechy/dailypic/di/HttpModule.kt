package com.yechy.dailypic.di

import com.yechy.dailypic.repository.http.ApodService
import com.yechy.dailypic.repository.http.BingService
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.support.ApodConverter
import com.yechy.dailypic.support.ApodConverterFactory
import com.yechy.dailypic.support.BingConverterFactory
import com.yechy.dailypic.util.APOD
import com.yechy.dailypic.util.APOD_BASE_URL
import com.yechy.dailypic.util.BING
import com.yechy.dailypic.util.BING_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
        RetrofitUrlManager.getInstance().putDomain(BING, BING_BASE_URL)
        RetrofitUrlManager.getInstance().putDomain(APOD, APOD_BASE_URL)

        return RetrofitUrlManager.getInstance().with(httpBuilder).build()
    }

    @Provides
    @Singleton
    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BING_BASE_URL)
        .client(okHttpClient)

    @Provides
    @Singleton
    fun createBingService(builder: Retrofit.Builder): BingService {
        val retrofit = builder.addConverterFactory(BingConverterFactory.create()).build()
        return retrofit.create(BingService::class.java)
    }

    @Provides
    @Singleton
    fun createApodService(builder: Retrofit.Builder): ApodService {
        val retrofit = builder.addConverterFactory(ApodConverterFactory.create()).build()
        return retrofit.create(ApodService::class.java)
    }

    @Provides
    @Singleton
    fun createHttpRepos(bingService: BingService, apodService: ApodService): HttpRepos =
        HttpRepos(bingService, apodService)

}