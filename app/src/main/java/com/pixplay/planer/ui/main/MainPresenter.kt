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
import westroom.checkbook2.data.models.adapter.ModelTask
import westroom.checkbook2.data.models.adapter.ModelTaskFromFB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class MainPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<MainContract.IView>(), MainContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var internetUtils: InternetUtils

    override fun initializeView() {
        val initializeView = iMvpView?.initializeView()

        dataManager.startTaskListener(object : IAppCallback<ArrayList<ModelTaskFromFB>> {
            override fun onSuccess(response: ArrayList<ModelTaskFromFB>) {
                response.forEach {
                    if(it.type == DocumentChange.Type.ADDED) {
                        if(it.modelTask.status == FRAME.FRAME10Years.name) iMvpView?.addItemTo10Year(it.modelTask)
                        if(it.modelTask.status == FRAME.FRAME1Year.name) iMvpView?.addItemTo1Year(it.modelTask)
                        if(it.modelTask.status == FRAME.FRAME1Mounth.name) iMvpView?.addItemTo1Mount(it.modelTask)
                        if(it.modelTask.status == FRAME.FRAMEGoods.name) iMvpView?.addItemToGood(it.modelTask)
                    }
                }
            }
        })

    }

    override fun actionTopMenu(itemId: Int) {
        when(itemId) {
            R.id.menu_clear -> iMvpView?.showAlertDialog(R.id.menu_exit, "Вы действиельно хотите очистить все задачи?")
            R.id.menu_exit -> iMvpView?.showAlertDialog(R.id.menu_exit, "Вы действиельно хотите выйти?")
        }
    }

    override fun actionAlertDialog(id: Int) {
        when(id) {
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

    override fun frame_main_actionTask(frame: FRAME, modelTask: ModelTask) {

    }

}