package com.yechy.dailypic.repository

import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
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
        arrayListOf(SourceInfo(it.url, "Bing"), SourceInfo(it.url, "APOD"))
    }
        .flowOn(Dispatchers.IO)
}
