package com.yechy.dailypic.ext

import io.reactivex.subjects.BehaviorSubject

/**
 *
 * Created by cloud on 2020-01-14.
 */
inline fun <reified T> BehaviorSubject<T>.copyMap(map: (T)->T) {
    val oldValue: T? = value
    if (oldValue != null) {
        onNext(map(oldValue))
    } else {
        throw NullPointerException("BehaviorSuject<${T::class.java}> not contain value.")
    }
}

inline fun <reified T> BehaviorSubject<T>.copyFlatMap(map: (T)->List<T>) {
    val oldValue: T? = value
    if (oldValue != null) {
        map(oldValue).forEach { onNext(it) }
    } else {
        throw NullPointerException("BehaviorSuject<${T::class.java}> not contain value.")
    }
}

