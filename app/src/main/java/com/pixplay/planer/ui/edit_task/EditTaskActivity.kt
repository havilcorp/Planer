package com.pixplay.planer.ui.edit_task

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import butterknife.ButterKnife
import com.pixplay.planer.App
import com.pixplay.planer.R
import com.pixplay.planer.base.BaseActivity
import com.pixplay.planer.data.models.adapter.ModelTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_new_task.*
import org.jetbrains.anko.padding
import javax.inject.Inject

class EditTaskActivity : BaseActivity(), EditTaskContract.IView {

    override fun inject() {
        App[this].component.inject(this)
    }

    @Inject lateinit var presenter: EditTaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        ButterKnife.bind(this)

        presenter.attachView(this)
        presenter.initializeView()
    }

    override fun initializeView() {

        edit_task_image.setOnClickListener {
            presenter.actionImage()
        }

        edit_task_actionSave.setOnClickListener {
            presenter.actionSave(
                edit_task_title.text.toString(),
                edit_task_description.text.toString())
        }

        intent.getStringExtra("id")?.let {
            presenter.loadTask(it)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onResultActivity(requestCode, resultCode, data, contentResolver)
    }

    override fun setTask(modelTask: ModelTask) {
        Picasso.get().load(modelTask.url).placeholder(R.drawable.shape_progress_bar_load).into(edit_task_image)
        edit_task_image.padding = 0
        edit_task_title.setText(modelTask.title)
        edit_task_description.setText(modelTask.description)
    }

    override fun setImage(bitmap: Bitmap) {
        edit_task_image.setImageBitmap(bitmap)
        edit_task_image.padding = 0
    }

}
