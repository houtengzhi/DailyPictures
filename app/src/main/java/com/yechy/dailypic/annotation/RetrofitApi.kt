package com.yechy.dailypic.annotation

/**
 *
 * Created by cloud on 2023/12/29.
 */


@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitApi(val apiName: String)
