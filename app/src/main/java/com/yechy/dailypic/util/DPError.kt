package com.yechy.dailypic.util

/**
 *
 * Created by cloud on 2024/1/3.
 */
enum class DPError(val errorCode: Int, val errorMessage: String) {

    NoContent(101, "No content"),

    SourceTypeNotFound(102, "Source type not found");
}