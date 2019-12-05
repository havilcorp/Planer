package com.pixplay.planer.ui.main

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import butterknife.ButterKnife
import com.pixplay.planer.App
import com.pixplay.planer.R
import com.pixplay.planer.base.BaseActivity
import com.pixplay.planer.data.models.menu.ModelMenu
import com.pixplay.planer.ui.main.fragments.FRAME
import com.pixplay.planer.ui.main.fragments.Fragment10Years
import com.unicornlight.ui.main.fragments.Fragment1Mount
import com.unicornlight.ui.main.fragments.Fragment1Year
import com.unicornlight.ui.main.fragments.FragmentGood
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundColor
import westroom.checkbook2.data.models.adapter.ModelIdString
import com.pixplay.planer.data.models.adapter.ModelTask
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.IView {

    val listFragments = ArrayList<ModelMenu>()
    private lateinit var fTrans: FragmentTransaction

    override fun inject() {
        App[this].component.inject(this)
    }

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        presenter.attachView(this)
        presenter.initializeView()
    }

    override fun initializeView() {

        //  init fragments
        listFragments.add(ModelMenu(Fragment10Years(), FRAME.FRAME10Years, "План на 10 лет"))
        listFragments.add(ModelMenu(Fragment1Year(), FRAME.FRAME1Year, "План на 1 год"))
        listFragments.add(ModelMenu(Fragment1Mount(), FRAME.FRAME1Mounth, "План на 1 месяц"))
        listFragments.add(ModelMenu(FragmentGood(), FRAME.FRAMEGoods, "Выполнено"))
        fTrans = supportFragmentManager.beginTransaction()
        listFragments.forEach { fTrans.add(R.id.main_frame, it.fragment) }
        fTrans.commit()
        //

        main_actionMenu1.setOnClickListener { presenter.actionMenu(listFragments[0]) }
        main_actionMenu2.setOnClickListener { presenter.actionMenu(listFragments[1]) }
        main_actionMenu3.setOnClickListener { presenter.actionMenu(listFragments[2]) }
        main_actionMenu4.setOnClickListener { presenter.actionMenu(listFragments[3]) }

        presenter.actionMenu(listFragments[0])

    }

    override fun showAlertDialog(id: Int, title: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setCancelable(true)
            .setPositiveButton("ОК") { dialog, id_ ->
                presenter.frame_main_OnActionAlertDialogResult(id)
                dialog.cancel()
            }
            .setNegativeButton("Отмена") { dialog, id -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun showListAlertDialog(frame: FRAME, list: ArrayList<String>) {
        val builderSingle = AlertDialog.Builder(this)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        list.forEach { arrayAdapter.add(it) }
        builderSingle.setNegativeButton("Отмена") { dialog, id -> dialog.cancel() }
        builderSingle.setAdapter(arrayAdapter) { dialog, which -> presenter.frame_main_OnActionTaskMenuResult(frame, which) }
        builderSingle.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.actionTopMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun activeButton(frame: FRAME) {
        main_actionMenu1.backgroundColor = Color.TRANSPARENT
        main_actionMenu2.backgroundColor = Color.TRANSPARENT
        main_actionMenu3.backgroundColor = Color.TRANSPARENT
        main_actionMenu4.backgroundColor = Color.TRANSPARENT
        if(frame == FRAME.FRAME10Years) main_actionMenu1.backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAME1Year) main_actionMenu2.backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAME1Mounth) main_actionMenu3.backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAMEGoods) main_actionMenu4.backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
    }

    override fun openFrame(frame: FRAME) {
        fTrans = supportFragmentManager.beginTransaction()
        listFragments.forEach { fTrans.detach(it.fragment) }
        listFragments.forEach { if(it.frame == frame) fTrans.attach(it.fragment) }
        fTrans.commit()
    }

    fun getFrame(frame: FRAME): Fragment {
        listFragments.forEach { if(it.frame == frame) return it.fragment }
        return Fragment()
    }



    // frame_10Years
    override fun setCount10Year(count: Int) {
        main_actionMenu1.text = "10 Лет ($count)"
    }
    override fun addItemTo10Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME10Years) as Fragment10Years).addToList(modelTask)
    }
    override fun modiferItemTo10Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME10Years) as Fragment10Years).modiferToList(modelTask)
    }
    override fun removeItemTo10Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME10Years) as Fragment10Years).removeToList(modelTask)
    }
    // frame_1Years
    override fun setCount1Year(count: Int) {
        main_actionMenu2.text = "1 Год ($count)"
    }
    override fun addItemTo1Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Year) as Fragment1Year).addToList(modelTask)
    }
    override fun modiferItemTo1Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Year) as Fragment1Year).modiferToList(modelTask)
    }
    override fun removeItemTo1Year(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Year) as Fragment1Year).removeToList(modelTask)
    }
    // frame_1Mount
    override fun setCount1Mount(count: Int) {
        main_actionMenu3.text = "1 Месяц ($count)"
    }
    override fun addItemTo1Mount(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Mounth) as Fragment1Mount).addToList(modelTask)
    }
    override fun modiferItemTo1Mount(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Mounth) as Fragment1Mount).modiferToList(modelTask)
    }
    override fun removeItemTo1Mount(modelTask: ModelTask) {
        (getFrame(FRAME.FRAME1Mounth) as Fragment1Mount).removeToList(modelTask)
    }
    // frame_good
    override fun setCountGood(count: Int) {
        main_actionMenu4.text = "Выполнено ($count)"
    }
    override fun addItemToGood(modelTask: ModelTask) {
        (getFrame(FRAME.FRAMEGoods) as FragmentGood).addToList(modelTask)
    }
    override fun modiferItemToGood(modelTask: ModelTask) {
        (getFrame(FRAME.FRAMEGoods) as FragmentGood).modiferToList(modelTask)
    }
    override fun removeItemToGood(modelTask: ModelTask) {
        (getFrame(FRAME.FRAMEGoods) as FragmentGood).removeToList(modelTask)
    }
}
