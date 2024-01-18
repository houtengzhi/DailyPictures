package com.yechy.dailypic.repository.db

import androidx.room.*
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.SourceInfo

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureSource(sourceInfo: SourceInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureSourceList(sourceList: List<SourceInfo>)

    @Update
    suspend fun updatePictureSource(sourceInfo: SourceInfo)

    @Query("SELECT * FROM source_list")
    suspend fun queryPictureSourceList(): List<SourceInfo>

    @Query("SELECT * FROM source_list WHERE type = :type")
    suspend fun queryPictureSourceWithType(type: Int): SourceInfo?

    @Delete
    suspend fun deletePictureSource(sourceInfo: SourceInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureList(pictureList: List<PictureInfo>)

    @Query("SELECT * FROM picture_list WHERE source_type = :type")
    suspend fun queryPictureList(type: Int): List<PictureInfo>
}