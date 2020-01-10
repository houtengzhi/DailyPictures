package com.yechy.dailypic.ui

import com.yechy.dailypic.repository.PictureInfo
import java.util.*

/**
 *
 * Created by cloud on 2019-12-03.
 */
data class MainViewState(
    val isLoading: Boolean,
    val pictureInfo: PictureInfo?,
    val throwable: Throwable?) {
    companion object {
        fun initial(): MainViewState {
            return MainViewState(isLoading = false, pictureInfo = null, throwable = null)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (javaClass != other?.javaClass) return false
        other as MainViewState

        if (isLoading != other.isLoading) return false
        if (pictureInfo != other.pictureInfo) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(isLoading, pictureInfo, throwable)
    }
}