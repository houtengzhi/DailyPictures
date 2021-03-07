package com.yechy.dailypic.ui

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.ext.copyMap
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.util.SingletonHolderSingleArg
import com.yechy.dailypic.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * Created by cloud on 2019-12-03.
 */
class MainViewModel @ViewModelInject constructor(val dataRepos: DataRepos): BaseViewModel() {
    private val mViewStateSubject: BehaviorSubject<MainViewState> = BehaviorSubject.createDefault(
        MainViewState.initial())

    fun observeViewState(): Observable<MainViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    fun getTodayPicture() {
        mViewStateSubject.copyMap {
            it.copy(isLoading = true, pictureList = null, throwable = null)
        }
        dataRepos.getTodayPicture()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe (
                { pictureInfoList ->
                    mViewStateSubject.copyMap {
                        it.copy(
                            isLoading = false,
                            pictureList = pictureInfoList,
                            throwable = null
                        )
                    }
                }, {t: Throwable? ->
                    mViewStateSubject.copyMap {
                        it.copy(
                            isLoading = false,
                            pictureList = null,
                            throwable = t
                        )
                    }
                }
                )
    }

}