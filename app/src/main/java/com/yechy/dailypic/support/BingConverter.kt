package com.yechy.dailypic.support

import com.yechy.dailypic.repository.PictureInfo
import com.yechy.dailypic.repository.http.ApiResponse
import com.yechy.dailypic.util.DPError
import com.yechy.dailypic.util.DataParser.parseBingData
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import kotlin.jvm.Throws

/**
 * Created by cloud on 2020-02-12.
 */
class BingConverter :
    Converter<ResponseBody, ApiResponse<List<PictureInfo>?>> {

    override fun convert(value: ResponseBody): ApiResponse<List<PictureInfo>> {
        val response = value.string()
        val data = parseBingData(response)
        if (data.isNotEmpty()) {
            return ApiResponse.Success(data)
        } else {
            return ApiResponse.Error(DPError.NoContent)
        }
    }
}