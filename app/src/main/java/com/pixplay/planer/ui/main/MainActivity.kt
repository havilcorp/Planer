package com.pixplay.planer.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
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
import org.jetbrains.anko.backgroundColor
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
        listFragments.add(ModelMenu(Fragment10Years(), FRAME.FRAME10Years, ""))
        listFragments.add(ModelMenu(Fragment1Year(), FRAME.FRAME1Year, ""))
        listFragments.add(ModelMenu(Fragment1Mount(), FRAME.FRAME1Mounth, ""))
        listFragments.add(ModelMenu(FragmentGood(), FRAME.FRAMEGoods, ""))
        fTrans = supportFragmentManager.beginTransaction()
        listFragments.forEach { fTrans.add(R.id.main_frame, it.fragment) }
        fTrans.commit()
        //

        findViewById<View>(R.id.main_actionMenu1).setOnClickListener { presenter.actionMenu(listFragments[0].frame) }
        findViewById<View>(R.id.main_actionMenu2).setOnClickListener { presenter.actionMenu(listFragments[1].frame) }
        findViewById<View>(R.id.main_actionMenu3).setOnClickListener { presenter.actionMenu(listFragments[2].frame) }
        findViewById<View>(R.id.main_actionMenu4).setOnClickListener { presenter.actionMenu(listFragments[3].frame) }

        openFrame(FRAME.FRAME10Years)
        activeButton(FRAME.FRAME10Years)

    }

    override fun activeButton(frame: FRAME) {
        findViewById<View>(R.id.main_actionMenu1).backgroundColor = Color.TRANSPARENT
        findViewById<View>(R.id.main_actionMenu2).backgroundColor = Color.TRANSPARENT
        findViewById<View>(R.id.main_actionMenu3).backgroundColor = Color.TRANSPARENT
        findViewById<View>(R.id.main_actionMenu4).backgroundColor = Color.TRANSPARENT
        if(frame == FRAME.FRAME10Years) findViewById<View>(R.id.main_actionMenu1).backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAME1Year) findViewById<View>(R.id.main_actionMenu2).backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAME1Mounth) findViewById<View>(R.id.main_actionMenu3).backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
        if(frame == FRAME.FRAMEGoods) findViewById<View>(R.id.main_actionMenu4).backgroundColor = ContextCompat.getColor(this, R.color.actionButton)
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

    // frame_10years

    //

    // frame_mode

    //

    //frame_color

    //

}
