package com.yechy.dailypic.base

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

/**
 *
 * Created by cloud on 2019-08-25.
 */
abstract class BaseInjectionFragment: Fragment(), KodeinAware {
    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)
}