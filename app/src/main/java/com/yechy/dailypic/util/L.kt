package com.yechy.dailypic.util

import android.util.Log

object L {
    var logSwitch = true
    var logLevel = 1
    private const val LOG_MAXLENGTH = 2000
    fun e(tag: String, msg: String) {
        if (logLevel <= Log.ERROR || logSwitch) {
            Log.e(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (logLevel <= Log.DEBUG || logSwitch) {
            Log.d(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (logLevel <= Log.WARN || logSwitch) {
            Log.w(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (logLevel <= Log.INFO || logSwitch) {
            Log.i(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (logLevel <= Log.VERBOSE || logSwitch) {
            Log.v(tag, msg)
        }
    }

    fun LongString(tag: String, msg: String) {
        if (logLevel <= Log.VERBOSE || logSwitch) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    e(tag, i.toString() + ": " + msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    e(tag, i.toString() + ": " + msg.substring(start, strLength))
                    break
                }
            }
        }
    }
}