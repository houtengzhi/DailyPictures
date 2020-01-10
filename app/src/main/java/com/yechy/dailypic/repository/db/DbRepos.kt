package com.yechy.dailypic.repository.db

import io.reactivex.Single

/**
 *
 * Created by cloud on 2019-08-29.
 */
class DbRepos(private val appDatabase: AppDatabase) {

    fun getWhiteList(): Single<List<AppEntity>> = appDatabase.appDao().queryAllAppEntityList()

    fun addAppToWhiteList(appEntity: AppEntity) = appDatabase.appDao().insertAppEntity(appEntity)

    fun deleteAppFromWhiteList(appEntity: AppEntity) = appDatabase.appDao().insertAppEntity(appEntity)
}