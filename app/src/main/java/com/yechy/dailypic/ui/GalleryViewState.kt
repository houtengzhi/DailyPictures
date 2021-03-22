package com.yechy.dailypic.ui

import com.yechy.dailypic.repository.PictureInfo
import java.util.*

/**
 *
 * Created by cloud on 2019-12-03.
 */
data class GalleryViewState(
    val isLoading: Boolean,
    val pictureList: List<PictureInfo>?,
    val throwable: Throwable?) {
    companion object {
        fun initial(): GalleryViewState {
            return GalleryViewState(isLoading = false, pictureList = null, throwable = null)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GalleryViewState

        if (isLoading != other.isLoading) return false
        if (pictureList != other.pictureList) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(isLoading, pictureList, throwable)
    }
}