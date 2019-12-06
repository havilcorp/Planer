package com.pixplay.planer.data

import android.util.Log

interface IAppCallback <T> {

    fun onSuccess(response: T) {}
    fun onFailure(message: String?, e: Throwable) {
        e.stackTrace.forEach {
            Log.d("TAG", "${it.className} ${it.lineNumber}")
        }
        Log.d("TAG", "ERROR: $message")
    }

}