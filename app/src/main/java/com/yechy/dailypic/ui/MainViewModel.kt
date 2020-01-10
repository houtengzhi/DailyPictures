package com.yechy.dailypic.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.util.SingletonHolderSingleArg
import com.yechy.dailypic.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * Created by cloud on 2019-12-03.
 */
class MainViewModel(private val dataRepos: DataRepos): BaseViewModel() {
    private val mViewStateSubject: BehaviorSubject<MainViewState> = BehaviorSubject.createDefault(
        MainViewState.initial())

    fun observeViewState(): Observable<MainViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    companion object {
        fun instance(fragment: Fragment, dataRepos: DataRepos): MainViewModel =
            ViewModelProviders.of(fragment, MainViewModelFactory.getInstance(dataRepos))
                .get(MainViewModel::class.java)
    }

    fun getTodayPicture() {
        mViewStateSubject.onNext(MainViewState(isLoading = false, pictureInfo = null, throwable = null))
        dataRepos.getTodayPicture()
            .autoDispose(this)
    }

}

class MainViewModelFactory(private val dataRepos: DataRepos): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(dataRepos) as T

    companion object : SingletonHolderSingleArg<MainViewModelFactory, DataRepos>(::MainViewModelFactory)
}