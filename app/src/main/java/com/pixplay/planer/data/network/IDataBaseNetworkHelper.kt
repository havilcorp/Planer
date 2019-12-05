package com.pixplay.planer.data.network

import android.graphics.Bitmap
import io.reactivex.Flowable
import com.pixplay.planer.data.models.adapter.ModelTask
import westroom.checkbook2.data.models.adapter.ModelTaskFromFB
import java.util.ArrayList

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

    fun startTaskListener(): Flowable<ArrayList<ModelTaskFromFB>>
    fun addNewTask(modelTask: ModelTask): Flowable<CODE>
    fun modiferStatusTask(id: String, status: String): Flowable<CODE>
    fun deleteTask(id: String): Flowable<CODE>
    fun getTask(id: String): Flowable<ModelTask>
    fun clearAllTasks(): Flowable<CODE>

}
