package com.pixplay.planer.ui.signUp

import com.pixplay.planer.data.mvp.IMvpView

class SignUpContract {

    interface IView: IMvpView {
        fun initializeView()

    }

    interface IPresenter {
        fun initializeView()

        fun actionSignUp(email: String, pass: String, rePass: String)

    }

}