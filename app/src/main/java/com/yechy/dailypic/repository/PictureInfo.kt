package com.yechy.dailypic.repository

/**
 *
 * Created by cloud on 2019-11-05.
 */
data class PictureInfo constructor (val url: String,
                     val title: String) {
    var mediaType: String? = null
    var copyRight: String? = null
    var copyrightonly: String? = null
    var hash: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var height: Int = -1
    var width: Int = -1
    var desc: String? = null
    var date: String? = null

    var hdUrl: String? = null
    var serviceVersion: String? = null

}