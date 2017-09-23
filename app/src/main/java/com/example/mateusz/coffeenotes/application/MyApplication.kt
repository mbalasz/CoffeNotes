package com.example.mateusz.coffeenotes.application

import android.app.Application
import android.content.Context

open class MyApplication : Application() {
    private lateinit var myAppComponent: MyAppComponent

    override fun onCreate() {
        super.onCreate()
        myAppComponent = createGraph()
    }

    open fun createGraph(): MyAppComponent {
        return DaggerMyAppComponent.builder().myAppModule(MyAppModule(this)).build()
    }

    fun getAppComponent(): MyAppComponent {
        return myAppComponent
    }

}