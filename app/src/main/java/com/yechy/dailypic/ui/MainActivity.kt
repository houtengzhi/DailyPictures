package com.yechy.dailypic.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * Created by cloud on 2019-10-15.
 */
@AndroidEntryPoint
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMain()
        }

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}