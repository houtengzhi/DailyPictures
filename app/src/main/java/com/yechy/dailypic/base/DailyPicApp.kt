package com.yechy.dailypic.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * Created by cloud on 2019-10-15.
 */
@HiltAndroidApp
open class DailyPicApp: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: DailyPicApp
    }
}