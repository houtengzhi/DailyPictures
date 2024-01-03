package com.yechy.dailypic.support

import com.yechy.dailypic.annotation.RetrofitApi
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by cloud on 2020-02-12.
 */
class AnnotationConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return annotations.filterIsInstance<RetrofitApi>()
            .firstOrNull()?.apiName?.let {
                if ("get_bing_images".equals(it)) {
                    BingConverter()
                } else if ("get_apod_images".equals(it)) {
                    ApodConverter()
                } else {
                    null
                }
            }
    }

    companion object {
        fun create(): AnnotationConverterFactory {
            return AnnotationConverterFactory()
        }
    }
}