package com.yechy.dailypic.repository.http

import com.yechy.dailypic.annotation.RetrofitApi
import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 *
 * Created by cloud on 2019-10-15.
 */
interface BingService {

    @Headers("Domain-Name: bing")
    @GET("HPImageArchive.aspx")
    @RetrofitApi("get_bing_images")
    suspend fun fetchHPImage(@Query("format") format: String,
                     @Query("idx") idx: Int,
                     @Query("n") n:Int,
                     @Query("ensearch") enSearch: Int,
                     @Query("pid") pid: String,
                     @Query("uhd") uhd: Int,
                     @Query("uhdwidth") uhdwidth: Int,
                     @Query("uhdheight") uhdheight: Int) : ApiResponse<List<PictureInfo>>

}