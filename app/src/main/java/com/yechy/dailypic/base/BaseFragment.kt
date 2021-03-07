package com.yechy.dailypic.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 *
 * Created by cloud on 2019-08-25.
 */
abstract class BaseFragment: Fragment() {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
    }
}