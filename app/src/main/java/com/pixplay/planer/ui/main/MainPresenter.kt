package com.pixplay.planer.ui.main

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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class MainPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<MainContract.IView>(), MainContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var internetUtils: InternetUtils

    override fun initializeView() {
        iMvpView?.initializeView()
    }

    override fun actionTopMenu(itemId: Int) {
        when(itemId) {
            R.id.menu_add -> iMvpView?.startActivity(NewTaskActivity::class.java, false)
            R.id.menu_clear -> {

            }
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

    override fun frame_main_actionTask(frame: FRAME, modelTask: ModelTask) {

    }

}