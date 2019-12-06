package com.pixplay.planer.ui.main

import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.pixplay.planer.R
import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.data.IAppCallback
import com.pixplay.planer.data.models.menu.ModelMenu
import com.pixplay.planer.data.network.CODE
import com.pixplay.planer.ui.main.fragments.FRAME
import com.pixplay.planer.ui.new_task.NewTaskActivity
import com.pixplay.planer.ui.signIn.SignInActivity
import com.pixplay.planer.utils.InternetUtils
import io.reactivex.disposables.CompositeDisposable
import westroom.checkbook2.data.models.adapter.ModelIdString
import com.pixplay.planer.data.models.adapter.ModelTask
import com.pixplay.planer.ui.edit_task.EditTaskActivity
import westroom.checkbook2.data.models.adapter.ModelTaskFromFB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class MainPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<MainContract.IView>(), MainContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var internetUtils: InternetUtils

    override fun initializeView() {
        iMvpView?.initializeView()

        dataManager.startTaskListener(object : IAppCallback<ArrayList<ModelTaskFromFB>> {
            override fun onSuccess(response: ArrayList<ModelTaskFromFB>) {
                response.forEach {
                    when(it.type) {
                        DocumentChange.Type.ADDED -> {
                            if(it.modelTask.status == FRAME.FRAME10Years.name) iMvpView?.addItemTo10Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Year.name) iMvpView?.addItemTo1Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Mounth.name) iMvpView?.addItemTo1Mount(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAMEGoods.name) iMvpView?.addItemToGood(it.modelTask)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            iMvpView?.removeItemTo10Year(it.modelTask)
                            iMvpView?.removeItemTo1Year(it.modelTask)
                            iMvpView?.removeItemTo1Mount(it.modelTask)
                            iMvpView?.removeItemToGood(it.modelTask)

                            if(it.modelTask.status == FRAME.FRAME10Years.name) iMvpView?.addItemTo10Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Year.name) iMvpView?.addItemTo1Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Mounth.name) iMvpView?.addItemTo1Mount(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAMEGoods.name) iMvpView?.addItemToGood(it.modelTask)
                        }
                        DocumentChange.Type.REMOVED -> {
                            if(it.modelTask.status == FRAME.FRAME10Years.name) iMvpView?.removeItemTo10Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Year.name) iMvpView?.removeItemTo1Year(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAME1Mounth.name) iMvpView?.removeItemTo1Mount(it.modelTask)
                            if(it.modelTask.status == FRAME.FRAMEGoods.name) iMvpView?.removeItemToGood(it.modelTask)
                        }
                    }
                }
            }
        })

    }

    override fun actionTopMenu(itemId: Int) {
        when(itemId) {
            R.id.menu_clear -> iMvpView?.showAlertDialog(R.id.menu_clear, "Вы действиельно хотите очистить все задачи?")
            R.id.menu_exit -> iMvpView?.showAlertDialog(R.id.menu_exit, "Вы действиельно хотите выйти?")
        }
    }

    override fun frame_main_OnActionAlertDialogResult(id: Int) {
        when(id) {
            R.id.menu_clear -> {
                dataManager.clearAllTasks(object : IAppCallback<CODE> {})
            }
            R.id.menu_exit -> {
                dataManager.signOut(object : IAppCallback<CODE> {
                    override fun onSuccess(response: CODE) {
                        iMvpView?.startActivity(SignInActivity::class.java, true)
                    }
                })
            }
        }
    }

    override fun actionMenu(modelMenu: ModelMenu) {
        iMvpView?.openFrame(modelMenu.frame)
        iMvpView?.activeButton(modelMenu.frame)
        iMvpView?.setTitle(modelMenu.title)
    }

    override fun frame_main_actionAdd(frame: FRAME) {
        iMvpView?.startActivity(NewTaskActivity::class.java, false, Intent().putExtra("status", frame.name))
    }

    var selectModelId = ""

    override fun frame_main_actionTaskMenu(frame: FRAME, modelTask: ModelTask) {
        selectModelId = modelTask.id
        val list = ArrayList<String>()
        list.add("Редактировать")
        list.add("Удалить")
        list.add("Перенести во вкладку Все мечты")
        list.add("Перенести во вкладку 1 год")
        list.add("Перенести во вкладку В работе")
        list.add("Перенести во вкладку Выполнено")
        iMvpView?.showListAlertDialog(frame, list)
    }

    override fun frame_main_OnActionTaskMenuResult(frame: FRAME, id: Int) {
        Log.d("TAG", "$id")
        when(id) {
            0 -> iMvpView?.startActivity(EditTaskActivity::class.java, false, Intent().putExtra("id", selectModelId))
            1 -> {
                dataManager.deleteTask(selectModelId, object : IAppCallback<CODE> {
                    override fun onSuccess(response: CODE) {
                        iMvpView?.message("Удалено")
                    }
                })
            }
            2 -> dataManager.modiferStatusTask(selectModelId, FRAME.FRAME10Years.name, object : IAppCallback<CODE> {})
            3 -> dataManager.modiferStatusTask(selectModelId, FRAME.FRAME1Year.name, object : IAppCallback<CODE> {})
            4 -> dataManager.modiferStatusTask(selectModelId, FRAME.FRAME1Mounth.name, object : IAppCallback<CODE> {})
            5 -> dataManager.modiferStatusTask(selectModelId, FRAME.FRAMEGoods.name, object : IAppCallback<CODE> {})
        }
    }

    override fun updateCountList10Year(count: Int) {
        iMvpView?.setCount10Year(count)
    }
    override fun updateCountList1Year(count: Int) {
        iMvpView?.setCount1Year(count)
    }
    override fun updateCountList1Mount(count: Int) {
        iMvpView?.setCount1Mount(count)
    }
    override fun updateCountListGood(count: Int) {
        iMvpView?.setCountGood(count)
    }

}