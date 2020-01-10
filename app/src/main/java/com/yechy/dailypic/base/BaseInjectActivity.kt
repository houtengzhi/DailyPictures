package com.yechy.dailypic.base

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

/**
 *
 * Created by cloud on 2019-08-25.
 */
abstract class BaseInjectActivity: AppCompatActivity(), KodeinAware {

    protected val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }
}