package com.yechy.dailypic.repository.http

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * Created by cloud on 2021/4/2.
 */
interface ApodService {

    @GET("apod")
    fun getFeed(@Query("api_key") api_key: String?): Flowable<ResponseBody>?

    @GET("apod")
    fun getFeedWithDate(
        @Query("api_key") api_key: String?,
        @Query("date") date: String?
    ): Flowable<ResponseBody>?

    @GET(".")
    fun getFeedThumbnail(): Flowable<ResponseBody>?

    @POST("translate")
    fun getTranslate(
        @Query("key") key: String?,
        @Query("lang") lang: String?,
        @Query("text") text: String?
    ): Flowable<ResponseBody>?

    companion object {
        const val API_KEY = "DEMO_KEY"
        const val TRANS_API_KEY = "DEMO_TRANS_KEY"
        const val AD_ID = "AD_KEY"
    }
}