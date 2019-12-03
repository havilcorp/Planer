package com.pixplay.planer.data.network

import io.reactivex.Flowable

/**
 * Created by havil on 19.03.2018.
 */

interface IDataBaseNetworkHelper {

    fun signUp(email: String, password: String): Flowable<CODE>
    fun signIn(email: String, password: String): Flowable<CODE>



}
