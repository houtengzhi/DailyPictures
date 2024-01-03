package com.yechy.dailypic.repository.bean

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by cloud on 2023/4/13.
 */
data class ApodBean(
    @SerializedName("copyright") val copyright: String,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("hdurl") val hdurl: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)
