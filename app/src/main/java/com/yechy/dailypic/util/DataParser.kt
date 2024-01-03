package com.yechy.dailypic.util

import android.text.TextUtils

import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.http.ApiResponse

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import com.yechy.dailypic.util.BING_BASE_URL
import org.json.JSONTokener

/**
 * Created by cloud on 2020-02-12.
 */
object DataParser {

    fun parseBingData(response: String): List<PictureInfo> {
        val pictureInfoList = mutableListOf<PictureInfo>()
        if (!TextUtils.isEmpty(response)) {
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("images")
                for (i in 0 until jsonArray.length()) {
                    val jsonObject1 = jsonArray.getJSONObject(i)
                    val startDate = jsonObject1.optString("startdate")
                    val fullStartDate = jsonObject1.optString("fullstartdate")
                    val endDate = jsonObject1.optString("enddate")
                    val url = jsonObject1.optString("url")
                    val urlbase = jsonObject1.optString("urlbase")
                    val copyRight = jsonObject1.optString("copyright")
                    val title = jsonObject1.optString("title")
                    val desc = jsonObject1.optString("desc")
                    val date = jsonObject1.optString("date")
                    val copyRightOnly = jsonObject1.optString("copyrightonly")
                    val hash = jsonObject1.optString("hsh")
                    val realUrl = BING_BASE_URL + url

                    val pictureInfo = PictureInfo(
                        SOURCE_TYPE_BING,
                        realUrl, title
                    )
                    pictureInfo.desc = desc
                    pictureInfo.copyRight = copyRight
                    pictureInfo.copyrightonly = copyRightOnly
                    pictureInfo.date = date
                    pictureInfo.startDate = startDate
                    pictureInfo.endDate = endDate
                    pictureInfo.hash = hash;

                    pictureInfoList.add(pictureInfo)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return pictureInfoList
    }

    fun parseApodData(response: String): ApiResponse<List<PictureInfo>> {
        val pictureInfoList = mutableListOf<PictureInfo>()
        if (!TextUtils.isEmpty(response)) {
            try {
                val tokener = JSONTokener(response).nextValue()
                if (tokener is JSONArray) {
                    tokener.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
                        .map {
                            val date = it.optString("date")
                            val explanation = it.optString("explanation")
                            val hdUrl = it.optString("hdurl")
                            val mediaType = it.optString("media_type")
                            val serviceVersion = it.optString("service_type")
                            val title = it.optString("title")
                            val url = it.optString("url")
                            val pictureInfo = PictureInfo(SOURCE_TYPE_APOD, url, title)
                            pictureInfo.date = date
                            pictureInfo.desc = explanation
                            pictureInfo.mediaType = mediaType
                            pictureInfo.hdUrl = hdUrl;
                            pictureInfo.serviceVersion = serviceVersion
                            pictureInfoList.add(pictureInfo)
                            return ApiResponse.Success(pictureInfoList)
                        }
                } else if (tokener is JSONObject) {
                    val code = tokener.optInt("code", 0)
                    if (code == 0) {
                        val date = tokener.optString("date")
                        val explanation = tokener.optString("explanation")
                        val hdUrl = tokener.optString("hdurl")
                        val mediaType = tokener.optString("media_type")
                        val serviceVersion = tokener.optString("service_type")
                        val title = tokener.optString("title")
                        val url = tokener.optString("url")

                        val pictureInfo = PictureInfo(SOURCE_TYPE_APOD, url, title)
                        pictureInfo.date = date
                        pictureInfo.desc = explanation
                        pictureInfo.mediaType = mediaType
                        pictureInfo.hdUrl = hdUrl;
                        pictureInfo.serviceVersion = serviceVersion
                        pictureInfoList.add(pictureInfo)
                        return ApiResponse.Success(pictureInfoList)
                    } else {
                        val msg = tokener.optString("msg")
                        return ApiResponse.Error(code, msg)
                    }

                }
            } catch (e: JSONException) {
                e.printStackTrace()
                return ApiResponse.Exception(e)
            }
        }
        return ApiResponse.Error(DPError.NoContent)
    }
}
