package com.yechy.dailypic.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yechy.dailypic.R
import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.SourceInfo
import com.yechy.dailypic.util.ImageLoader
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * Created by cloud on 2021/3/18.
 */
class PictureSourceAdapter(val pictureSourceList: MutableList<SourceInfo>, private val onItemClick: (SourceInfo) -> Unit)
    : RecyclerView.Adapter<PictureSourceAdapter.ViewHolder>() {

    fun setData(dataList: List<SourceInfo>) {
        pictureSourceList.clear()
        pictureSourceList.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_picture_source, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pictureSourceList[position])
    }

    override fun getItemCount() = pictureSourceList.size

    class ViewHolder(view: View, private val onItemClick: (SourceInfo) -> Unit): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_source_name)
        val ivBackground: ImageView = view.findViewById(R.id.iv_source_background)
        val layoutItem: View = view.findViewById(R.id.layout_item_source)

        fun bind(sourceInfo: SourceInfo) {
            tvName.text = sourceInfo.title
            ImageLoader.load(ivBackground.context, ivBackground, sourceInfo.url)
            ivBackground.setOnClickListener {
                onItemClick(sourceInfo)
            }
        }
    }
}