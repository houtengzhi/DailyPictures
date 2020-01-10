package com.yechy.dailypic.repository.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Dao
interface AppDao {

    @Insert()
    fun insertAppEntity(appEntity: AppEntity): Single<Long>

    @Delete
    fun deleteAppEntity(appEntity: AppEntity): Single<Int>

    @Query("SELECT * FROM white_list")
    fun queryAllAppEntityList(): Single<List<AppEntity>>
}