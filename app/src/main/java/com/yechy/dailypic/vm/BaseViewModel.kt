package com.yechy.dailypic.vm

import androidx.lifecycle.ViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.CompletableSource
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * Created by cloud on 2019-08-25.
 */
open class BaseViewModel: ViewModel(), LifecycleScopeProvider<BaseViewModel.ViewModelEvent> {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    override fun requestScope(): CompletableSource {
        return super.requestScope()
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> {
            when (it) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException("Cannot bind to ViewModel lifecycle after onCleared.")
            }
        }
    }
}