package com.pixplay.planer.ui.new_task

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pixplay.planer.base.BasePresenter
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.data.IAppCallback
import com.pixplay.planer.data.network.CODE
import com.pixplay.planer.ui.main.fragments.FRAME
import com.pixplay.planer.utils.ImageUtils
import com.pixplay.planer.utils.InternetUtils
import io.reactivex.disposables.CompositeDisposable
import westroom.checkbook2.data.models.adapter.ModelTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class NewTaskPresenter
@Inject constructor(private val dataManager: DataManager): BasePresenter<NewTaskContract.IView>(),
    NewTaskContract.IPresenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var internetUtils: InternetUtils
    val CAMERA_ACTION_GALERY = 100
    var loadImage: Bitmap? = null


    override fun initializeView() {
        iMvpView?.initializeView()
        iMvpView?.setTitle("Новая мечта")
    }

    override fun onResultActivity(requestCode: Int, resultCode: Int, data: Intent?, contentResolver: ContentResolver) {
        if(resultCode == Activity.RESULT_OK && requestCode == CAMERA_ACTION_GALERY && data != null){
            val imageUri = data.data
            val imageStream = contentResolver.openInputStream(imageUri!!)
            val imageBitmap = BitmapFactory.decodeStream(imageStream)
            loadImage = imageBitmap
            iMvpView?.setImage(imageBitmap)
        }
    }

    @SuppressLint("IntentReset")
    override fun actionImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        iMvpView?.startActivityFResult(intent, CAMERA_ACTION_GALERY)
    }

    override fun actionSave(title: String, description: String, status: String) {
        if(title.isEmpty() || description.isEmpty()) iMvpView?.message("Не вcе данные были заполнены!")
        else loadImage?.let { loadImage ->
            iMvpView?.showProgress()
            dataManager.loadPhotoAndGetUrlName(loadImage, object : IAppCallback<String> {
                override fun onSuccess(imageUrl: String) {
                    dataManager.addNewTask(ModelTask(imageUrl, title, description, status), object : IAppCallback<CODE> {
                        override fun onSuccess(response: CODE) {
                            iMvpView?.hideProgress()
                            if(response == CODE.SUCCESS) {
                                iMvpView?.message("Добавлено")
                                iMvpView?.backView()
                            }
                        }
                    })
                }
            })
        }
    }

}