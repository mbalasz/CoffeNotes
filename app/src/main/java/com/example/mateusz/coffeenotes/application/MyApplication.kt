package com.example.mateusz.coffeenotes.application

import android.app.Application
import android.content.Context

open class MyApplication : Application() {
    private lateinit var myAppComponent: MyAppComponent

    override fun onCreate() {
        super.onCreate()
        myAppComponent = MyAppComponent(this)
    }

    open fun getAppComponent(): MyAppComponent {
        return myAppComponent
    }

    companion object {
        fun get(context: Context): MyApplication {
            return context.applicationContext as MyApplication
        }
    }
}