package com.pixplay.planer.ui.signUp

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.pixplay.planer.App
import com.pixplay.planer.R
import com.pixplay.planer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject


class SignUpActivity : BaseActivity(), SignUpContract.IView {

    override fun inject() {
        App[this].component.inject(this)
    }

    @Inject lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ButterKnife.bind(this)

        presenter.attachView(this)
        presenter.initializeView()
    }

    override fun initializeView() {
        findViewById<View>(R.id.signUp_actionSignUp).setOnClickListener {
            presenter.actionSignUp(
                signUp_email.text.toString(),
                signUp_pass.text.toString(),
                signUp_rePass.text.toString()
            )
        }
    }

}
