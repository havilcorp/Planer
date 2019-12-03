package com.pixplay.planer.ui.logo

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.ui.signIn.SignInActivity
import com.pixplay.planer.utils.InternetUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class LogoPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<LogoContract.IView>(), LogoContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var internetUtils: InternetUtils

    override fun initializeView() {
        iMvpView?.initializeView()

        iMvpView?.startActivity(SignInActivity::class.java, true)
    }

}