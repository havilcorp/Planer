package com.pixplay.planer.ui.remember_pass

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class RememberPassPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<RememberPassContract.IView>(),
    RememberPassContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun initializeView() {
        iMvpView?.initializeView()
        iMvpView?.setTitle("Восстановить пароль")
    }

}