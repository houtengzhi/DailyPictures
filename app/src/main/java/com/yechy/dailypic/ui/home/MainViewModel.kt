package com.yechy.dailypic.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.ext.copyMap
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.ui.DataState
import com.yechy.dailypic.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by cloud on 2021/3/22.
 */
@HiltViewModel
class MainViewModel @Inject constructor(val dataRepos: DataRepos): BaseViewModel() {

    private var _sourceList = MutableLiveData<DataState<List<SourceInfo>>>(DataState.inital())
    val sourceList get() = _sourceList

    init {
        viewModelScope.launch {
            dataRepos.getPictureSourceList()
                .onStart {
                    _sourceList.copyMap { it.copy(true,null,null) }
                }
                .catch { e ->
                    _sourceList.copyMap { it.copy(false, null, e) }
                }
                .collect { infoList ->
                    _sourceList.copyMap {
                        it.copy(false, infoList,null) }
                }
        }
    }

}