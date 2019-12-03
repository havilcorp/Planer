package com.pixplay.planer.ui.new_task

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import butterknife.ButterKnife
import com.pixplay.planer.App
import com.pixplay.planer.R
import com.pixplay.planer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_new_task.*
import org.jetbrains.anko.padding
import javax.inject.Inject

class NewTaskActivity : BaseActivity(), NewTaskContract.IView {

    override fun inject() {
        App[this].component.inject(this)
    }

    @Inject lateinit var presenter: NewTaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        ButterKnife.bind(this)

        presenter.attachView(this)
        presenter.initializeView()
    }

    override fun initializeView() {

        new_task_image.setOnClickListener {
            presenter.actionImage()
        }

        new_task_actionSave.setOnClickListener {
            val status = intent.getStringExtra("status")
            status?.let { status ->
                presenter.actionSave(
                    new_task_title.text.toString(),
                    new_task_description.text.toString(),
                    status
                )
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onResultActivity(requestCode, resultCode, data, contentResolver)
    }

    override fun setImage(bitmap: Bitmap) {
        new_task_image.setImageBitmap(bitmap)
        new_task_image.padding = 0
    }

}
