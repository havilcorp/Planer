package com.pixplay.planer.data

import com.pixplay.planer.data.network.CODE
import io.reactivex.disposables.Disposable

interface IDataManager {

    fun signUp(email: String, password: String, handler: IAppCallback<CODE>): Disposable
    fun signIn(email: String, password: String, handler: IAppCallback<CODE>): Disposable
}