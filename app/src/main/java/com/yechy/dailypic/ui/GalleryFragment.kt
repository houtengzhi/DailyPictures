package com.yechy.dailypic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 *
 * Created by cloud on 2019-12-03.
 */
@AndroidEntryPoint
class GalleryFragment: BaseFragment() {

    val mViewModel: GalleryViewModel by lazy { ViewModelProvider(this).get(GalleryViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = GalleryPagerAdaper(requireContext(), mutableListOf())

        mViewModel.observeViewState()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({state ->
                    progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                if (state.throwable != null) {
                    state.throwable.printStackTrace()
                }
                if (state.pictureList != null) {
                    (viewPager.adapter as GalleryPagerAdaper).setData(state.pictureList)
                    (viewPager.adapter as GalleryPagerAdaper).notifyDataSetChanged()
                }
            }, {throwable ->
                throwable.printStackTrace()
            })
    }

    override fun onStart() {
        super.onStart()
        mViewModel.getTodayPicture()
    }
}