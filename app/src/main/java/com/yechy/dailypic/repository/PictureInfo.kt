package com.yechy.dailypic.repository

/**
 *
 * Created by cloud on 2019-11-05.
 */
data class PictureInfo (val url: String,
                     val title: String,
                     val desc: String,
                        val copyRight: String,
                     val copyrightonly: String,
                     val date: String,
                     val startDate: String,
                     val endDate: String,
                     val height: Int,
                     val width: Int,
                        val hash: String) {

}