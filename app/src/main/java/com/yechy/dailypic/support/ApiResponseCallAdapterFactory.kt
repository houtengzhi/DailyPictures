package com.yechy.dailypic.support

import com.yechy.dailypic.repository.http.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * Created by cloud on 2023/4/13.
 */
class ApiResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        if (!(returnType is ParameterizedType)) {
            throw IllegalArgumentException(
                    "return type must be parameterized as Call<Foo> or Call<? extends Foo>")
        }
        val apiResponseType = getParameterUpperBound(0, returnType)
        if (getRawType(apiResponseType) != ApiResponse::class.java) return null
        if (!(apiResponseType is ParameterizedType)) {
            throw IllegalArgumentException(
                "return type must be parameterized")
        }
        val dataType = getParameterUpperBound(0, apiResponseType)
        return ApiResponseCallAdapter(dataType)
    }

    companion object {

        fun create() = ApiResponseCallAdapterFactory()
    }
}