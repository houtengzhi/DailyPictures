package com.yechy.dailypic.repository

import com.yechy.dailypic.repository.db.DbRepos
import com.yechy.dailypic.repository.http.HttpRepos
import com.yechy.dailypic.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


/**
 *
 * Created by cloud on 2019-08-25.
 */
class DataRepos(private val dbRepos: DbRepos, private val httpRepos: HttpRepos) {

    fun getDailyPictureList(sourceType: Int): Flow<List<PictureInfo>> = flow {
        val pictureList = dbRepos.queryPictureList(sourceType)
        if (pictureList.isEmpty()) {
            val data = httpRepos.fetchDailyPictureList()
            dbRepos.insertPictureList(data)
            emit(data)
        } else {
            emit(pictureList)
        }
    }
        .flowOn(Dispatchers.IO)

    fun getPictureSourceList(): Flow<List<SourceInfo>> = flow<List<SourceInfo>> {
        val sourceList = dbRepos.queryPictureSourceList()
        if (sourceList.isEmpty()) {
            val bingPictureInfo = httpRepos.fetchTodayPictureInfo()
            val data = arrayListOf(
                SourceInfo(bingPictureInfo.url, "Bing", SOURCE_TYPE_BING),
                SourceInfo(bingPictureInfo.url, "APOD", SOURCE_TYPE_APOD),
                SourceInfo(bingPictureInfo.url, "TEST1", SOURCE_TYPE_TEST1),
                SourceInfo(bingPictureInfo.url, "TEST2", SOURCE_TYPE_TEST2),
                SourceInfo(bingPictureInfo.url, "TEST3", SOURCE_TYPE_TEST3)
            )
            dbRepos.insertPictureSourceList(data)
            emit(data)
        } else {
            emit(sourceList)
        }
    }
        .flowOn(Dispatchers.IO)
}
