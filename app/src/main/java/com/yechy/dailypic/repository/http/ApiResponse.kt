package com.yechy.dailypic.repository.http

import com.google.gson.annotations.SerializedName
import com.yechy.dailypic.util.DPError
import retrofit2.Response

/**
 * Created by cloud on 2020-02-12.
 */
public sealed interface ApiResponse<out T> {

    data class Success<T>(val data: T) : ApiResponse<T> {
    }
    data class Error(val errorCode: Int, val errorMessage: String) : ApiResponse<Nothing> {

        constructor(dpError: DPError): this(dpError.errorCode, dpError.errorMessage)

        override fun toString(): String {
            return "Error(errorCode=$errorCode, errorMessage='$errorMessage')"
        }
    }
    data class Exception(val throwable: Throwable) : ApiResponse<Nothing> {
        override fun toString(): String {
            return "Exception(throwable=${throwable.message})"
        }
    }

    companion object {

    }

}