package com.yechy.dailypic.util

import android.content.Context
import android.widget.ImageView
import com.yechy.dailypic.R

/**
 * Created by cloud on 2019-12-20.
 */
object ImageLoader {
    private const val TAG = "ImageLoader"
    fun load(
        context: Context?,
        imageView: ImageView?,
        url: String?
    ) {
        L.d(TAG, "load(), url=$url")
        GlideApp.with(context!!)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageView!!)
    }
}