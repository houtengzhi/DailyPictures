package com.yechy.dailypic.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.ext.copyMap
import com.yechy.dailypic.repository.DataRepos
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.ui.DataState
import com.yechy.dailypic.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by cloud on 2019-12-03.
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(val dataRepos: DataRepos): BaseViewModel() {

    private val _pictureList = MutableLiveData<DataState<List<PictureInfo>>>(DataState.inital())

    val pictureList get() = _pictureList

    init {

    }

    fun getPicturesList(id: Int) {
        viewModelScope.launch {
            dataRepos.getTodayPicture()
                .onStart { _pictureList.copyMap { it.copy(true, null, null) } }
                .catch { e -> _pictureList.copyMap { it.copy(false, null, e) } }
                .collect { pictureList -> _pictureList.copyMap { it.copy(false, pictureList, null) } }
        }
    }
}