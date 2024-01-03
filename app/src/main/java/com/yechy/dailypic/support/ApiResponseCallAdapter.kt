package com.yechy.dailypic.support

import com.yechy.dailypic.repository.http.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 *
 * Created by cloud on 2023/4/13.
 */
class ApiResponseCallAdapter(private val responseType: Type) : CallAdapter<Any, Call<Any>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Any>): Call<Any> {
        return ApiResponseCall(call)
    }
}