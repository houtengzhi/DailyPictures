package com.yechy.dailypic.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseFragment
import com.yechy.dailypic.ui.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *
 * Created by cloud on 2021/3/18.
 */
@AndroidEntryPoint
class MainFragment: BaseFragment() {

    val mViewModel: GalleryViewModel by lazy { ViewModelProvider(this).get(GalleryViewModel::class.java) }

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
        mRecyclerView.adapter = PictureSourceAdapter(mutableListOf())
    }

    override fun onStart() {
        super.onStart()
    }
}