package com.pixplay.planer.ui.edit_task

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import com.pixplay.planer.data.models.adapter.ModelTask
import com.pixplay.planer.data.mvp.IMvpView
import com.pixplay.planer.ui.main.fragments.FRAME

class EditTaskContract {

    interface IView: IMvpView {
        fun initializeView()
        fun setImage(bitmap: Bitmap)
        fun setTask(modelTask: ModelTask)
    }

    interface IPresenter {
        fun initializeView()

        fun loadTask(id: String)

        fun onResultActivity(requestCode: Int, resultCode: Int, data: Intent?, contentResolver: ContentResolver)

        fun actionImage()
        fun actionSave(title: String, description: String)

    }

}