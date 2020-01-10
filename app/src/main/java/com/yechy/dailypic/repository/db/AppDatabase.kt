package com.yechy.dailypic.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 * Created by cloud on 2019-08-25.
 */
@Database(entities = [AppEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}