package com.pixplay.planer.ui.signUp

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.ui.main.MainActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class SignUpPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<SignUpContract.IView>(),
    SignUpContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun initializeView() {
        iMvpView?.initializeView()
    }

    override fun actionSignUp() {
        iMvpView?.startActivity(MainActivity::class.java, true)
    }

}