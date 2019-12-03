package com.pixplay.planer.data

import android.graphics.Bitmap
import com.pixplay.planer.data.local.DataBaseLocalHelper
import com.pixplay.planer.data.network.CODE
import com.pixplay.planer.data.network.DataBaseNetworkHelper
import io.reactivex.disposables.Disposable
import westroom.checkbook2.data.models.adapter.ModelTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton open class DataManager @Inject constructor(
    private var dataBaseLocalHelper: DataBaseLocalHelper,
    private val dataBaseNetworkHelper: DataBaseNetworkHelper
): IDataManager {

    override fun signUp(email: String, password: String, handler: IAppCallback<CODE>): Disposable {
        return dataBaseNetworkHelper.signUp(email, password).subscribe(
            { handler.onSuccess(it) },
            { handler.onFailure(it.message, it) }
        )
    }

    override fun signIn(email: String, password: String, handler: IAppCallback<CODE>): Disposable {
        return dataBaseNetworkHelper.signIn(email, password).subscribe(
            { handler.onSuccess(it) },
            { handler.onFailure(it.message, it) }
        )
    }

    override fun isAuth() = dataBaseNetworkHelper.isAuth()

    override fun signOut(handler: IAppCallback<CODE>): Disposable {
        return dataBaseNetworkHelper.signOut().subscribe(
            { handler.onSuccess(it) },
            { handler.onFailure(it.message, it) }
        )
    }

    //

    override fun loadPhotoAndGetUrlName(bitmap: Bitmap, handler: IAppCallback<String>): Disposable {
        return dataBaseNetworkHelper.loadPhotoAndGetUrlName(bitmap).subscribe(
            { handler.onSuccess(it) },
            { handler.onFailure(it.message, it) }
        )
    }

    //

    override fun addNewTask(modelTask: ModelTask, handler: IAppCallback<CODE>): Disposable {
        return dataBaseNetworkHelper.addNewTask(modelTask).subscribe(
            { handler.onSuccess(it) },
            { handler.onFailure(it.message, it) }
        )
    }

}