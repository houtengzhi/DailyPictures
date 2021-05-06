package com.yechy.dailypic.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by cloud on 2021/4/27.
 */
class DividerItemDecoration : ItemDecoration() {
    private var mDividerHeight = 1f
    private val mPaint: Paint
    private var leftMargin = 0f
    private var rightMargin = 0f
    fun setLeftMargin(margin: Float): DividerItemDecoration {
        leftMargin = margin
        return this
    }

    fun setRightMargin(margin: Float): DividerItemDecoration {
        rightMargin = margin
        return this
    }

    fun setColor(color: Int): DividerItemDecoration {
        mPaint.color = color
        return this
    }

    fun setDividerHeight(height: Float): DividerItemDecoration {
        mDividerHeight = height
        return this
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = mDividerHeight.toInt()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        //因为getItemOffsets是针对每一个ItemView，而onDraw方法是针对RecyclerView本身，所以需要循环遍历来设置
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            //第一个ItemView不需要绘制
            if (index == 0) {
                continue
            }
            val dividerTop = view.top - mDividerHeight //？？？为啥是减：因为坐标是下为正右为正，所以它的上边就得减
            val dividerLeft = parent.paddingLeft + leftMargin //加入偏移量
            val dividerBottom = view.top.toFloat()
            val dividerRight = parent.width - parent.paddingRight - rightMargin //加入偏移量
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint)
        }
    }

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#33787880")
    }
}