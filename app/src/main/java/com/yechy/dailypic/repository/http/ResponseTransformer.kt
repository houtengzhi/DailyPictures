package com.yechy.dailypic.repository.http

/**
 *
 * Created by cloud on 2023/4/13.
 */
@JvmSynthetic
public inline fun <T> ApiResponse<T>.onSuccess(
    crossinline onResult: ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
public suspend inline fun <T> ApiResponse<T>.suspendOnSuccess(
    crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
public inline fun <T> ApiResponse<T>.onError(
    crossinline onResult: ApiResponse.Error.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
public suspend inline fun <T> ApiResponse<T>.suspendOnError(
    crossinline onResult: suspend ApiResponse.Error.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
public inline fun <T> ApiResponse<T>.onException(
    crossinline onResult: ApiResponse.Exception.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Exception) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
public suspend inline fun <T> ApiResponse<T>.suspendOnException(
    crossinline onResult: suspend ApiResponse.Exception.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Exception) {
        onResult(this)
    }
    return this
}

@Suppress("UNCHECKED_CAST")
public fun <T, V> ApiResponse<T>.mapSuccess(transformer: T.() -> V): ApiResponse<V> {
    if (this is ApiResponse.Success<T>) {
        return ApiResponse.Success(transformer(data))
    }
    return this as ApiResponse<V>
}

@JvmSynthetic
@Suppress("UNCHECKED_CAST")
public suspend fun <T, V> ApiResponse<T>.suspendMapSuccess(
    transformer: suspend T.() -> V,
): ApiResponse<V> {
    if (this is ApiResponse.Success<T>) {
        val invoke = transformer.invoke(data)
        return ApiResponse.Success(invoke)
    }
    return this as ApiResponse<V>
}