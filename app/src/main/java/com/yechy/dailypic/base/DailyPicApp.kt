package com.yechy.dailypic.base

import android.app.Application
import android.content.Context
import com.yechy.dailypic.di.appModule
import com.yechy.dailypic.di.dbModule
import com.yechy.dailypic.di.httpModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.Singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 *
 * Created by cloud on 2019-10-15.
 */
open class DailyPicApp: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@DailyPicApp }
        import(androidCoreModule(this@DailyPicApp))
        import(androidXModule(this@DailyPicApp))
        import(appModule)
        import(httpModule)
        import(dbModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: DailyPicApp
    }
}