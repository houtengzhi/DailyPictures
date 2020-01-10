package com.yechy.dailypic.repository.http

import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Created by cloud on 2019-10-15.
 */
interface BingService {

    @GET("HPImageArchive.aspx")
    fun fetchHPImage(@Query("format") format: String,
                     @Query("idx") idx: Int,
                     @Query("n") n:Int,
                     @Query("mkt") mkt: String) : Flowable<PictureInfo>

}