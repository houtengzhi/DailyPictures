package com.yechy.dailypic.di

import androidx.room.Room
import com.yechy.dailypic.base.DailyPicApp
import com.yechy.dailypic.repository.db.AppDatabase
import com.yechy.dailypic.repository.db.DbRepos
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *
 * Created by cloud on 2019-08-25.
 */
private const val DB_MODULE_TAG = "DBModule"

private const val SP_MODULE_TAG = "SPModule"

private const val DATABASE_NAME = "raven.db"

val dbModule = Kodein.Module(DB_MODULE_TAG) {
    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(DailyPicApp.INSTANCE, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    bind<DbRepos>() with singleton {
        DbRepos(appDatabase = instance())
    }
}

val spModule = Kodein.Module(SP_MODULE_TAG) {

}