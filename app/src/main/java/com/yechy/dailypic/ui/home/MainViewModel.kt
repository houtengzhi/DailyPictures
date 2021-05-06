package com.yechy.dailypic.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.ext.copyMap
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.ui.DataState
import com.yechy.dailypic.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch

/**
 *
 * Created by cloud on 2021/3/22.
 */
class MainViewModel @ViewModelInject constructor(val dataRepos: DataRepos): BaseViewModel() {

    private val mDataStateSubject: BehaviorSubject<DataState<List<SourceInfo>>> = BehaviorSubject.createDefault(
        DataState.inital()
    )

    fun observeDataState(): Observable<DataState<List<SourceInfo>>> {
        return mDataStateSubject.hide().distinctUntilChanged()
    }

    fun getPictureSourceList() {
        mDataStateSubject.copyMap {
            it.copy(isLoading = true, data = null, throwable = null)
        }
        viewModelScope.launch {
            try {
                val sourceLsit = dataRepos.getPictureSourceList()
                mDataStateSubject.copyMap {
                    it.copy(isLoading = false, data = sourceLsit, throwable = null)
                }
            } catch (e: Exception) {
                mDataStateSubject.copyMap {
                    it.copy(isLoading = false, data = null, throwable = e)
                }
            }
        }
    }

}