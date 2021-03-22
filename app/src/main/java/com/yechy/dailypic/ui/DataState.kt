package com.yechy.dailypic.ui

import com.yechy.dailypic.repository.PictureInfo

/**
 *
 * Created by cloud on 2021/3/22.
 */
data class DataState<T>(val isLoading: Boolean,
                     val data: T?,
                     val throwable: Throwable?) {
    companion object {
        fun <T> inital(): DataState<T> {
            return DataState<T>(isLoading = false, data = null, throwable = null)
        }
    }

}
