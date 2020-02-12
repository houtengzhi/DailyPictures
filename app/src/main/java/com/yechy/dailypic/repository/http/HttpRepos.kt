package com.yechy.dailypic.repository.http

import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.Flowable

/**
 *
 * Created by cloud on 2019-11-05.
 */
class HttpRepos(val bingService: BingService) {

    fun fetchDailyPictureInfo(): Flowable<List<PictureInfo>> {
        return bingService.fetchHPImage("js", 0, 3, "")
            .map { it.data }
    }
}