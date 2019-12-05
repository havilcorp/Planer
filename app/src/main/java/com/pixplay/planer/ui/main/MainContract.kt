package com.pixplay.planer.ui.main

import com.pixplay.planer.data.models.menu.ModelMenu
import com.pixplay.planer.data.mvp.IMvpView
import com.pixplay.planer.ui.main.fragments.FRAME
import westroom.checkbook2.data.models.adapter.ModelIdString
import com.pixplay.planer.data.models.adapter.ModelTask

class MainContract {

    interface IView: IMvpView {
        fun initializeView()

        fun showAlertDialog(id: Int, title: String)
        fun showListAlertDialog(frame: FRAME, list: ArrayList<String>)

        fun openFrame(frame: FRAME)
        fun activeButton(frame: FRAME)

        fun setCount10Year(count: Int)
        fun setCount1Year(count: Int)
        fun setCount1Mount(count: Int)
        fun setCountGood(count: Int)

        fun addItemTo10Year(modelTask: ModelTask)
        fun addItemTo1Year(modelTask: ModelTask)
        fun addItemTo1Mount(modelTask: ModelTask)
        fun addItemToGood(modelTask: ModelTask)

        fun modiferItemTo10Year(modelTask: ModelTask)
        fun modiferItemTo1Year(modelTask: ModelTask)
        fun modiferItemTo1Mount(modelTask: ModelTask)
        fun modiferItemToGood(modelTask: ModelTask)

        fun removeItemTo10Year(modelTask: ModelTask)
        fun removeItemTo1Year(modelTask: ModelTask)
        fun removeItemTo1Mount(modelTask: ModelTask)
        fun removeItemToGood(modelTask: ModelTask)

    }

    interface IPresenter {
        fun initializeView()

        fun actionTopMenu(itemId: Int)
        fun frame_main_OnActionAlertDialogResult(id: Int)
        fun frame_main_OnActionTaskMenuResult(frame: FRAME, id: Int)

        fun actionMenu(modelMenu: ModelMenu)
        fun frame_main_actionTaskMenu(frame: FRAME, modelTask: ModelTask)
        fun frame_main_actionAdd(frame: FRAME)

        fun updateCountList10Year(count: Int)
        fun updateCountList1Year(count: Int)
        fun updateCountList1Mount(count: Int)
        fun updateCountListGood(count: Int)

    }

}