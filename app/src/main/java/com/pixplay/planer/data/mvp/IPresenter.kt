package com.pixplay.planer.data.mvp

interface IPresenter<V: IMvpView> {

    fun attachView(iMvpView: V)
    fun detachView()

}