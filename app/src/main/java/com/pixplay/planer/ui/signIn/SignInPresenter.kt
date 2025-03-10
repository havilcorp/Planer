package com.pixplay.planer.ui.signIn

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.data.IAppCallback
import com.pixplay.planer.data.network.CODE
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
        iMvpView?.setTitle("Авторизация")
    }

    override fun actionSignIn(email: String, pass: String) {
        iMvpView?.showProgress()
        dataManager.signIn(email, pass, object : IAppCallback<CODE> {
            override fun onSuccess(response: CODE) {
                iMvpView?.hideProgress()
                if(response == CODE.SUCCESS) {
                    iMvpView?.startActivity(MainActivity::class.java, true)
                }
            }
            override fun onFailure(message: String?, e: Throwable) {
                super.onFailure(message, e)
                iMvpView?.hideProgress()
                iMvpView?.message("Ошибка")
            }
        })
    }

    override fun actionSignUp() {
        iMvpView?.startActivity(SignUpActivity::class.java, false)
    }

}