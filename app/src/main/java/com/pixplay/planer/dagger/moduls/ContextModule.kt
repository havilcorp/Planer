package com.pixplay.planer.dagger.moduls

import android.content.Context
import com.pixplay.planer.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ContextModule (private val application: App){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}