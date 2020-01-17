package com.yechy.dailypic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.uber.autodispose.autoDispose
import com.yechy.dailypic.R
import com.yechy.dailypic.base.BaseFragment
import com.yechy.dailypic.repository.PictureInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 *
 * Created by cloud on 2019-12-03.
 */
class MainFragment: BaseFragment() {
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainModule)
    }

    private val mViewModel: MainViewModel by instance()
    private val mAdapter = GalleryPagerAdaper(context, ArrayList<PictureInfo>())

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
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = mAdapter

        mViewModel.observeViewState()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({state ->
                    progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                if (state.throwable != null) {
                    state.throwable.printStackTrace()
                }
                if (state.pictureList != null) {
                    mAdapter.setData(state.pictureList)
                    mAdapter.notifyDataSetChanged()
                }
            }, {throwable ->
                throwable.printStackTrace()
            })
    }
}