package com.yechy.dailypic.util

import android.text.TextUtils

import com.yechy.dailypic.repository.PictureInfo

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import com.yechy.dailypic.util.BING_BASE_URL

/**
 * Created by cloud on 2020-02-12.
 */
object DataParser {

    fun parseBingData(response: String): List<PictureInfo>? {
        if (!TextUtils.isEmpty(response)) {
            val pictureInfoList = ArrayList<PictureInfo>()
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
                        realUrl, title, desc, copyRight, copyRightOnly,
                        date, startDate, endDate, 0, 0, hash
                    )
                    pictureInfoList.add(pictureInfo)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return pictureInfoList
        }
        return null
    }
}
