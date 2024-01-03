package com.yechy.dailypic.support

import com.yechy.dailypic.repository.http.ApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 * Created by cloud on 2023/4/13.
 */
class ApiResponseCall(private val delegate: Call<Any>) : Call<Any> {

    override fun enqueue(callback: Callback<Any>) {

        delegate.enqueue(object : Callback<Any> {

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null) {
                        callback.onResponse(this@ApiResponseCall, Response.success(ApiResponse.Error(-1, "")))
                    } else {
                        callback.onResponse(this@ApiResponseCall, Response.success(body))
                    }
                } else {
                    callback.onResponse(this@ApiResponseCall, Response.success(ApiResponse.Error(response.code(), response.message())))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onResponse(this@ApiResponseCall, Response.success(ApiResponse.Exception(t)))
            }

        })
    }

    override fun clone(): Call<Any> {
        return ApiResponseCall(delegate)
    }

    override fun execute(): Response<Any> {
        return delegate.execute()
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}