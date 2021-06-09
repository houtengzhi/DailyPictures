package com.yechy.dailypic.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

/**
 *
 * Created by cloud on 2021/5/19.
 */
inline fun <reified T> MutableLiveData<T>.copyMap(map: (T)->T) {
    val oldValue: T? = value
    if (oldValue != null) {
        postValue(map(oldValue))
    } else {
        throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
    }
}