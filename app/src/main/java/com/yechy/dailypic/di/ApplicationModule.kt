package com.yechy.dailypic.di

import com.yechy.dailypic.repository.DataRepos
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *
 * Created by cloud on 2019-08-25.
 */
private const val MODULE_NAME = "Application_Module"

val appModule = Kodein.Module(MODULE_NAME, false) {

    bind<DataRepos>() with singleton {
        DataRepos(dbRepos = instance(), httpRepos = instance())
    }
}