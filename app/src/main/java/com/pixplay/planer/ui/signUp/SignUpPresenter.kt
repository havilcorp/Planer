package com.pixplay.planer.ui.signUp

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.data.IAppCallback
import com.pixplay.planer.data.network.CODE
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
        iMvpView?.setTitle("Регистрация")
    }

    override fun actionSignUp(email: String, pass: String, rePass: String) {
        if(pass == rePass) {
            iMvpView?.showProgress()
            dataManager.signUp(email, pass, object : IAppCallback<CODE> {
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
        } else iMvpView?.message("Пароли не совпадают")
    }

}