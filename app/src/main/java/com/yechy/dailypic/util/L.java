package com.yechy.dailypic.util;

import android.util.Log;

import androidx.annotation.NonNull;

public class L {
    public static boolean logSwitch = true;
    public static int logLevel = 1;
    private static String version = "";
    private static int LOG_MAXLENGTH = 2000;

    public static void e(String tag, String msg) {
        if (logLevel <= Log.ERROR || logSwitch) {
            Log.e(version + tag, msg);
        }
    }

    public static void e(String tag, String format, Object... args) {
        e(tag, formatMessage(format, args));
    }

    public static void d(String tag, String msg) {
        if (logLevel <= Log.DEBUG || logSwitch) {
            Log.d(version + tag, msg);
        }
    }

    public static void d(String tag, String format, Object... args) {
        d(tag, formatMessage(format, args));
    }

    public static void w(String tag, String msg) {
        if (logLevel <= Log.WARN || logSwitch) {
            Log.w(version + tag, msg);
        }
    }

    public static void w(String tag, String format, Object... args) {
        w(tag, formatMessage(format, args));
    }

    public static void i(String tag, String msg) {
        if (logLevel <= Log.INFO || logSwitch) {
            Log.i(version + tag, msg);
        }
    }

    public static void i(String tag, String format, Object... args) {
        i(tag, formatMessage(format, args));
    }

    public static void v(String tag, String msg) {
        if (logLevel <= Log.VERBOSE || logSwitch) {
            Log.v(version + tag, msg);
        }
    }

    public static void v(String tag, String format, Object... args) {
        v(tag, formatMessage(format, args));
    }

    public static void LongString(String tag, String msg) {
        if (logLevel <= Log.VERBOSE || logSwitch) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    L.e(tag, i + ": " + msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    L.e(tag, i + ": " + msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    private static String formatMessage(@NonNull String message, @NonNull Object[] args) {
        return String.format(message, args);
    }

}
