package com.yechy.dailypic.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.github.chrisbanes.photoview.PhotoView
import com.yechy.dailypic.R
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.util.ImageLoader

/**
 * Created by cloud on 2019-12-20.
 */
class GalleryPagerAdaper(
    private val mContext: Context,
    private val pictureInfoList: MutableList<PictureInfo>
) : PagerAdapter() {

    fun setData(dataList: List<PictureInfo>) {
        pictureInfoList.clear()
        pictureInfoList.addAll(dataList)
    }

    override fun getCount(): Int {
        return pictureInfoList?.size ?: 0
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_gallery_item, container, false)
        val photoView: PhotoView = view.findViewById(R.id.pv_gallery_item_image)
        val textView = view.findViewById<TextView>(R.id.tv_gallery_item_copyright)
        if (pictureInfoList != null && pictureInfoList.size > position) {
            val (url, _, _, copyRight) = pictureInfoList[position]
            ImageLoader.load(mContext, photoView, url)
            textView.text = copyRight
        }
        container.addView(
            view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

}