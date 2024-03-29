package com.yechy.dailypic.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.SourceInfo

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Database(entities = [SourceInfo::class, PictureInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}