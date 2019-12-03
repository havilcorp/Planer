package com.pixplay.planer.ui.main

import com.pixplay.planer.data.models.menu.ModelMenu
import com.pixplay.planer.data.mvp.IMvpView
import com.pixplay.planer.ui.main.fragments.FRAME
import westroom.checkbook2.data.models.adapter.ModelTask

class MainContract {

    interface IView: IMvpView {
        fun initializeView()

        fun showAlertDialog(id: Int, title: String)

        fun openFrame(frame: FRAME)
        fun activeButton(frame: FRAME)

        fun addItemTo10Year(modelTask: ModelTask)
        fun addItemTo1Year(modelTask: ModelTask)
        fun addItemTo1Mount(modelTask: ModelTask)
        fun addItemToGood(modelTask: ModelTask)

    }

    interface IPresenter {
        fun initializeView()

        fun actionTopMenu(itemId: Int)
        fun actionAlertDialog(id: Int)

        fun actionMenu(modelMenu: ModelMenu)
        fun frame_main_actionTask(frame: FRAME, modelTask: ModelTask)
        fun frame_main_actionAdd(frame: FRAME)

    }

}