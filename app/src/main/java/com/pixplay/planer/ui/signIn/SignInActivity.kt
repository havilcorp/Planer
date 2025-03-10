package com.pixplay.planer.ui.signIn

import android.os.Bundle
import butterknife.ButterKnife
import com.pixplay.planer.App
import com.pixplay.planer.R
import com.pixplay.planer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject

class SignInActivity : BaseActivity(), SignInContract.IView {

    override fun inject() {
        App[this].component.inject(this)
    }

    @Inject lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        ButterKnife.bind(this)

        presenter.attachView(this)
        presenter.initializeView()
    }

    override fun initializeView() {

        signIn_actionSignIn.setOnClickListener {
            presenter.actionSignIn(signIn_email.text.toString(), signIn_password.text.toString())
        }

        signIn_actionSignUp.setOnClickListener {
            presenter.actionSignUp()
        }

    }

}
