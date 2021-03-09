package com.yechy.dailypic.support

import com.yechy.dailypic.repository.http.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by cloud on 2020-02-12.
 */
class BingConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return if (type === ApiResponse::class.java) {
            BingConverter()
        } else {
            BingConverter()
        }
    }

    companion object {
        fun create(): BingConverterFactory {
            return BingConverterFactory()
        }
    }
}