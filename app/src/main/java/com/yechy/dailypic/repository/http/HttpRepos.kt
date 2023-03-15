package com.yechy.dailypic.repository.http

import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.Flowable

/**
 *
 * Created by cloud on 2019-11-05.
 */
const val FORMAT = "js"

class HttpRepos(val bingService: BingService, val apodService: ApodService) {

    suspend fun fetchDailyPictureList(): List<PictureInfo> {
        return bingService.fetchHPImage(FORMAT, 0, 8, 1, "hp", 1, 3840, 2160)
            .data
    }

    suspend fun fetchTodayPictureInfo(): PictureInfo {
        return bingService.fetchHPImage(FORMAT, 0, 1, 1, "hp", 1, 3840, 2160)
            .data.get(0)
    }
}