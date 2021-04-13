package com.yechy.dailypic.support

import com.yechy.dailypic.repository.http.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * Created by cloud on 2021/4/13.
 */
class ApodConverterFactory: Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return if (type == ApiResponse::class.java) {
            ApodConverter()
        } else {
            ApodConverter()
        }
    }

    companion object {
        fun create(): ApodConverterFactory {
            return ApodConverterFactory()
        }
    }
}