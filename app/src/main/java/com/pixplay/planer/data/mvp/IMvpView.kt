package com.pixplay.planer.data.mvp

import android.content.Intent

interface IMvpView {

    fun message(message : String?)
    fun showError(message: String?)

    fun setTitle(t: String)

    fun showProgress()
    fun hideProgress()
    fun toBuffer(text: String)
    fun hideKayboard()
    fun toast(data: Any)
    fun startActivity(clazz: Class<*>, isFinish: Boolean, intent: Intent = Intent())
    fun startActivityFResult(clazz: Class<*>, code: Int, intent: Intent = Intent())
    fun startActivityFResult(intent: Intent, code: Int)
    fun backView()
    fun setBackView()

}