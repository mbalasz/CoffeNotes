package com.example.mateusz.coffeenotes.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

open class MyApplication : Application() {
    private lateinit var myAppComponent: MyAppComponent

    override fun onCreate() {
        super.onCreate()
        initAndroidThreeTen()
        myAppComponent = createGraph()
    }

    open protected fun initAndroidThreeTen() {
        AndroidThreeTen.init(this)
    }

    open protected fun createGraph(): MyAppComponent {
        return DaggerMyAppComponent.builder().myAppModule(MyAppModule(this)).build()
    }

    fun getAppComponent(): MyAppComponent {
        return myAppComponent
    }

}