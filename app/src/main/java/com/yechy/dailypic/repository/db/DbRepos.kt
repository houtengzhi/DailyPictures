package com.yechy.dailypic.repository.db

import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.SourceInfo

/**
 *
 * Created by cloud on 2019-08-29.
 */
class DbRepos(private val appDatabase: AppDatabase) {

    suspend fun insertPictureSource(sourceInfo: SourceInfo) =
        appDatabase.appDao().insertPictureSource(sourceInfo)

    suspend fun insertPictureSourceList(sourceList: List<SourceInfo>) =
        appDatabase.appDao().insertPictureSourceList(sourceList)

    suspend fun queryPictureSourceList(): List<SourceInfo> =
        appDatabase.appDao().queryPictureSourceList()

    suspend fun queryPictureSourceWithType(type: Int): SourceInfo? =
        appDatabase.appDao().queryPictureSourceWithType(type)

    suspend fun insertPictureList(pictureList: List<PictureInfo>) =
        appDatabase.appDao().insertPictureList(pictureList)

    suspend fun queryPictureList(sourceType: Int): List<PictureInfo> =
        appDatabase.appDao().queryPictureList(sourceType)
}