package com.pixplay.planer.ui.new_task

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import com.pixplay.planer.data.mvp.IMvpView
import com.pixplay.planer.ui.main.fragments.FRAME

class NewTaskContract {

    interface IView: IMvpView {
        fun initializeView()

        fun setImage(bitmap: Bitmap)

    }

    interface IPresenter {
        fun initializeView()

        fun onResultActivity(requestCode: Int, resultCode: Int, data: Intent?, contentResolver: ContentResolver)

        fun actionImage()
        fun actionSave(title: String, description: String, status: String)

    }

}