package com.pixplay.planer.dagger.components

import com.pixplay.planer.App
import com.pixplay.planer.dagger.moduls.ContextModule
import com.pixplay.planer.dagger.moduls.FirebaseModule
import com.pixplay.planer.data.DataManager
import com.pixplay.planer.data.local.DataBaseLocalHelper
import com.pixplay.planer.data.network.DataBaseNetworkHelper
import com.pixplay.planer.ui.logo.LogoActivity
import com.pixplay.planer.ui.main.MainActivity
import com.pixplay.planer.ui.signIn.SignInActivity
import com.pixplay.planer.ui.signUp.SignUpActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,  FirebaseModule::class])
interface IDaggerComponent {

    fun inject(app: App)
    fun inject(activity: LogoActivity)
    fun inject(activity: SignUpActivity)
    fun inject(activity: SignInActivity)
    fun inject(activity: MainActivity)

    var dataManager : DataManager
    var dataBaseNetworkHelper : DataBaseNetworkHelper
    var dataBaseLocalHelper : DataBaseLocalHelper

}
