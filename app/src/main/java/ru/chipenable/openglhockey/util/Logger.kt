package ru.chipenable.openglhockey.util

import android.util.Log

/**
 * Created by Pavel.B on 26.05.2018.
 */

var isDebugLoggerEnabled = true

fun debugLogger(tag: String, action: (() -> String)){
    if (isDebugLoggerEnabled){
        Log.d(tag, action.invoke())
    }
}