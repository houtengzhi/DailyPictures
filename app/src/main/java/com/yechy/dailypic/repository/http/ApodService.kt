package com.yechy.dailypic.repository.http

import com.yechy.dailypic.annotation.RetrofitApi
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.bean.ApodBean
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * Created by cloud on 2021/4/2.
 */
interface ApodService {

    @Headers("Domain-Name: apod")
    @GET("apod")
    @RetrofitApi("get_apod_images")
    suspend fun getFeedWithDate(
        @Query("api_key") api_key: String?,
        @Query("date") date: String?
    ): ApiResponse<List<PictureInfo>>

    @Headers("Domain-Name: apod")
    @GET("apod")
    @RetrofitApi("get_apod_images")
    suspend fun getFeedInDateRange(
        @Query("api_key") api_key: String?,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?
    ): ApiResponse<List<PictureInfo>>

    @GET(".")
    fun getFeedThumbnail(): ApiResponse<ResponseBody>

    @POST("translate")
    fun getTranslate(
        @Query("key") key: String?,
        @Query("lang") lang: String?,
        @Query("text") text: String?
    ): ApiResponse<ResponseBody>

    companion object {
        const val API_KEY = "DEMO_KEY"
        const val TRANS_API_KEY = "DEMO_TRANS_KEY"
        const val AD_ID = "AD_KEY"
        const val DATE_FORMAT = "yyyy-MM-dd"
    }
}