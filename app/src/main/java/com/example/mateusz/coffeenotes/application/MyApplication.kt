package com.example.mateusz.coffeenotes.application

import android.app.Application
import android.content.Context

open class MyApplication : Application() {
    companion object {
        lateinit var myAppComponent: MyAppComponent
    }

    override fun onCreate() {
        super.onCreate()
        myAppComponent = DaggerMyAppComponent.builder().myAppModule(MyAppModule(this)).build()
    }

    open fun getAppComponent(): MyAppComponent {
        return myAppComponent
    }
}