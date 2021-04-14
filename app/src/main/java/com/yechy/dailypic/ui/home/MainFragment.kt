package com.yechy.dailypic.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseFragment
import com.yechy.dailypic.repository.SourceInfo
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *
 * Created by cloud on 2021/3/18.
 */
@AndroidEntryPoint
class MainFragment: BaseFragment() {

    val mViewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView.adapter = PictureSourceAdapter(mutableListOf(), this::onItemClick)
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 20)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        mViewModel.observeDataState()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({dataState ->
                if (dataState.data != null) {
                    (mRecyclerView.adapter as PictureSourceAdapter).setData(dataState.data)
                    (mRecyclerView.adapter as PictureSourceAdapter).notifyDataSetChanged()
                }

            }, { throwable ->
                throwable.printStackTrace()
            })

        mViewModel.getPictureSourceList()
    }

    private fun onItemClick(sourceInfo: SourceInfo) {
        Snackbar.make(mRecyclerView, "clicked", Snackbar.LENGTH_SHORT).show()
    }
}