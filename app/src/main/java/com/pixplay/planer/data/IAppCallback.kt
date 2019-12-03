package com.pixplay.planer.data

import android.util.Log

interface IAppCallback <T> {

    fun onSuccess(response: T) {}
    fun onFailure(message: String?, e: Throwable) {
        Log.d("TAG", "ERROR: $message")
    }

}