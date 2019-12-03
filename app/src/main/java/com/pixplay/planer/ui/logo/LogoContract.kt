package com.pixplay.planer.ui.logo

import com.pixplay.planer.data.mvp.IMvpView

class LogoContract {

    interface IView: IMvpView {
        fun initializeView()

    }

    interface IPresenter {
        fun initializeView()

    }

}