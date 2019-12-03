package com.pixplay.planer.data.network

import android.graphics.Bitmap
import io.reactivex.Flowable
import westroom.checkbook2.data.models.adapter.ModelTask

/**
 * Created by havil on 19.03.2018.
 */

interface IDataBaseNetworkHelper {

    fun signUp(email: String, password: String): Flowable<CODE>
    fun signIn(email: String, password: String): Flowable<CODE>
    fun isAuth(): Boolean
    fun signOut(): Flowable<CODE>

    //

    fun loadPhotoAndGetUrlName(bitmap: Bitmap): Flowable<String>

    //

    fun addNewTask(modelTask: ModelTask): Flowable<CODE>


}
