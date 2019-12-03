package com.pixplay.planer.ui.main

import com.pixplay.planer.data.models.menu.ModelMenu
import com.pixplay.planer.data.mvp.IMvpView
import com.pixplay.planer.ui.main.fragments.FRAME
import westroom.checkbook2.data.models.adapter.ModelTask

class MainContract {

    interface IView: IMvpView {
        fun initializeView()

        fun openFrame(frame: FRAME)
        fun activeButton(frame: FRAME)

    }

    interface IPresenter {
        fun initializeView()

        fun actionMenu(frame: FRAME)
        fun frame_main_actionTask(frame: FRAME, modelTask: ModelTask)

    }

}