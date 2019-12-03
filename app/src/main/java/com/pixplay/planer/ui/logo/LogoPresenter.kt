package com.pixplay.planer.ui.logo

import android.os.Handler
import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.ui.main.MainActivity
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

        Handler().postDelayed({
            if(dataManager.isAuth()) iMvpView?.startActivity(MainActivity::class.java, true)
            else iMvpView?.startActivity(SignInActivity::class.java, true)
        }, 1000)

    }

}