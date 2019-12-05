package com.pixplay.planer.data

import android.graphics.Bitmap
import com.pixplay.planer.data.network.CODE
import io.reactivex.disposables.Disposable
import com.pixplay.planer.data.models.adapter.ModelTask
import westroom.checkbook2.data.models.adapter.ModelTaskFromFB

interface IDataManager {

    fun signUp(email: String, password: String, handler: IAppCallback<CODE>): Disposable
    fun signIn(email: String, password: String, handler: IAppCallback<CODE>): Disposable
    fun isAuth(): Boolean
    fun signOut(handler: IAppCallback<CODE>): Disposable

    //

    fun loadPhotoAndGetUrlName(bitmap: Bitmap, handler: IAppCallback<String>): Disposable

    //

    fun startTaskListener(handler: IAppCallback<ArrayList<ModelTaskFromFB>>): Disposable
    fun addNewTask(modelTask: ModelTask, handler: IAppCallback<CODE>): Disposable
    fun modiferStatusTask(id: String, status: String, handler: IAppCallback<CODE>): Disposable
    fun deleteTask(id: String, handler: IAppCallback<CODE>): Disposable
    fun getTask(id: String, handler: IAppCallback<ModelTask>): Disposable
    fun clearAllTasks(handler: IAppCallback<CODE>): Disposable
}