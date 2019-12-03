package com.pixplay.planer.ui.remember_pass

import com.pixplay.planer.data.mvp.IMvpView

class RememberPassContract {

    interface IView: IMvpView {
        fun initializeView()

    }

    interface IPresenter {
        fun initializeView()


    }

}