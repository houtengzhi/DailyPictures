package com.yechy.dailypic.di

import com.yechy.dailypic.repository.http.BingService
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.support.BingConverterFactory
import com.yechy.dailypic.util.BING_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 *
 * Created by cloud on 2019-11-06.
 */
private const val MODULE_NAME = "HttpModule"

val httpModule = Kodein.Module(MODULE_NAME, false) {
    bind<OkHttpClient>() with singleton { createOkHttpClient() }
    bind<Retrofit>() with singleton { createRetrofit(instance()) }
    bind<BingService>() with singleton { createBingService(instance()) }
    bind<HttpRepos>() with singleton {
        HttpRepos(bingService = instance())
    }
}

private fun createOkHttpClient(): OkHttpClient {
    val httpBuilder = OkHttpClient.Builder()
    val httpLoggingInteractor = HttpLoggingInterceptor()
    httpLoggingInteractor.level = HttpLoggingInterceptor.Level.BODY
    httpBuilder.addInterceptor(httpLoggingInteractor)

    return httpBuilder.build()
}

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BING_BASE_URL)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .addConverterFactory(BingConverterFactory.create())
    .build()

private fun createBingService(retrofit: Retrofit): BingService = retrofit.create(BingService::class.java)