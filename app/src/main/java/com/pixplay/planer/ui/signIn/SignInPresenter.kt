package com.pixplay.planer.ui.signIn

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.ui.main.MainActivity
import com.pixplay.planer.ui.signUp.SignUpActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class SignInPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<SignInContract.IView>(),
    SignInContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun initializeView() {
        iMvpView?.initializeView()
    }

    override fun actionSignIn() {
        iMvpView?.startActivity(MainActivity::class.java, true)
    }

    override fun actionSignUp() {
        iMvpView?.startActivity(SignUpActivity::class.java, false)
    }

}