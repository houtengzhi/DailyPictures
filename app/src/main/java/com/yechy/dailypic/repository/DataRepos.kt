package com.yechy.dailypic.repository

import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.util.SOURCE_TYPE_APOD
import com.yechy.dailypic.util.SOURCE_TYPE_BING
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


/**
 *
 * Created by cloud on 2019-08-25.
 */
class DataRepos(private val dbRepos: DbRepos, private val httpRepos: HttpRepos) {

    fun getTodayPicture(): Flow<List<PictureInfo>> = flow {
        emit(httpRepos.fetchDailyPictureInfo())
    }
        .flowOn(Dispatchers.IO)

    fun getPictureSourceList(): Flow<List<SourceInfo>> = flow<PictureInfo> {
        val bingPictureInfo = httpRepos.fetchTodayPictureInfo()
        emit(bingPictureInfo)
    }.map {
        arrayListOf(SourceInfo(it.url, "Bing", SOURCE_TYPE_BING), SourceInfo(it.url, "APOD", SOURCE_TYPE_APOD),
            SourceInfo(it.url, "TEST1", SOURCE_TYPE_BING), SourceInfo(it.url, "TEST2", SOURCE_TYPE_BING),
            SourceInfo(it.url, "TEST3", SOURCE_TYPE_BING))
    }
        .flowOn(Dispatchers.IO)
}
