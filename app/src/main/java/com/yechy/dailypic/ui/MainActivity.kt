package com.yechy.dailypic.ui

import android.os.Bundle
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseActivity

/**
 *
 * Created by cloud on 2019-10-15.
 */
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction().add(R.id.fl_container, MainFragment(), TAG).commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}