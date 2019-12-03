package com.pixplay.planer.ui.main

import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.ui.main.fragments.FRAME
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

    override fun actionMenu(frame: FRAME) {
        iMvpView?.openFrame(frame)
        iMvpView?.activeButton(frame)
    }

    override fun frame_main_actionTask(frame: FRAME, modelTask: ModelTask) {

    }

}