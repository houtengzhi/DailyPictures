package com.yechy.dailypic.support

import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.http.ApiResponse
import com.yechy.dailypic.util.DataParser
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 *
 * Created by cloud on 2021/4/2.
 */
class ApodConverter: Converter<ResponseBody, ApiResponse<List<PictureInfo>?>> {
    override fun convert(value: ResponseBody): ApiResponse<List<PictureInfo>> {
        val response = value.string()
        val data = DataParser.parseApodData(response)
        return data
    }
}