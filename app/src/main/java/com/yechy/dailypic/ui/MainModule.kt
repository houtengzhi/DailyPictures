package com.yechy.dailypic.ui

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

/**
 *
 * Created by cloud on 2020-01-03.
 */
private const val MODULE_NAME = "MAIN_MODULE"

val mainModule = Kodein.Module(MODULE_NAME) {
    bind<MainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MainViewModel.instance(context, instance())
    }
}