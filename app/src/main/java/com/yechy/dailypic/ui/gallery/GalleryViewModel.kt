package com.yechy.dailypic.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.ext.copyMap
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch

/**
 *
 * Created by cloud on 2019-12-03.
 */
class GalleryViewModel @ViewModelInject constructor(val dataRepos: DataRepos): BaseViewModel() {
    private val mViewStateSubject: BehaviorSubject<GalleryViewState> = BehaviorSubject.createDefault(
        GalleryViewState.initial()
    )

    fun observeViewState(): Observable<GalleryViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun getTodayPicture() {
        mViewStateSubject.copyMap {
            it.copy(isLoading = true, pictureList = null, throwable = null)
        }
        viewModelScope.launch {
            try {
                val  pictureList = dataRepos.getTodayPicture()
                mViewStateSubject.copyMap {
                    it.copy(
                        isLoading = false,
                        pictureList = pictureList,
                        throwable = null
                    )
                }
            } catch (e: Exception) {
                mViewStateSubject.copyMap {
                    it.copy(
                        isLoading = false,
                        pictureList = null,
                        throwable = e
                    )
                }
            }


        }
    }

}