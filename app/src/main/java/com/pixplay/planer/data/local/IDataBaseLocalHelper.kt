package com.pixplay.planer.data.local

import android.content.Context
import com.pixplay.planer.data.network.CODE
import io.reactivex.Flowable

interface IDataBaseLocalHelper {

    fun saveLocalData(context: Context, key: String, data: String): Flowable<CODE>
    fun getLocalData(context: Context, key: String): Flowable<String>
    fun saveLocalDataBool(context: Context, key: String, data: Boolean): Flowable<CODE>
    fun getLocalDataBool(context: Context, key: String): Flowable<Boolean>

}