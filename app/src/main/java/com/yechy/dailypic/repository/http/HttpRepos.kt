package com.yechy.dailypic.repository.http

import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.Flowable

/**
 *
 * Created by cloud on 2019-11-05.
 */
const val FORMAT = "js"

class HttpRepos(val bingService: BingService) {

    fun fetchDailyPictureInfo(): Flowable<List<PictureInfo>> {
        return bingService.fetchHPImage(FORMAT, 0, 8, 1, "hp", 1, 3840, 2160)
            .map { it.data }
    }

    fun fetchTodayPictureInfo(): Flowable<PictureInfo> {
        return bingService.fetchHPImage(FORMAT, 0, 1, 1, "hp", 1, 3840, 2160)
            .map { it.data.get(0) }
    }
}