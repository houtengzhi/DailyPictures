package com.yechy.dailypic.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.yechy.dailypic.R

/**
 *
 * Created by cloud on 2021/4/19.
 */
class TransparentActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent)
    }

    override fun onResume() {
        super.onResume()
        Handler().post {
            val view = findViewById<View>(android.R.id.content)
            showSnackbar(view, Snackbar.LENGTH_INDEFINITE)
        }
    }

    private fun showSnackbar(view: View, duration: Int) {
        val s = SpannableString("Snackbar")
        s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val snackbar = Snackbar.make(view, s, duration)
        val layout: Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
        layout.getChildAt(0).setVisibility(View.GONE)
        layout.setBackground(null)
        val content: View =
            LayoutInflater.from(view.context).inflate(R.layout.layout_snackbar_with_progress, null)
        (content.findViewById<View>(R.id.text) as TextView).setText(s)
        layout.setPadding(0, 0, 0, 0)
        layout.addView(content, 0)
        snackbar.show()
    }
}