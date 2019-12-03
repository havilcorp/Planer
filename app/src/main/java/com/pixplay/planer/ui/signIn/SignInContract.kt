package com.pixplay.planer.ui.signIn

import com.pixplay.planer.data.mvp.IMvpView

class SignInContract {

    interface IView: IMvpView {
        fun initializeView()

    }

    interface IPresenter {
        fun initializeView()

        fun actionSignIn()
        fun actionSignUp()

    }

}