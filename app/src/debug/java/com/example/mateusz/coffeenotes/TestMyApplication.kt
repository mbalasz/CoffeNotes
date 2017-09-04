package com.example.mateusz.coffeenotes

import android.content.Context
import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.application.TestMyAppComponent

class TestMyApplication: MyApplication() {
    lateinit var testMyAppComponent: TestMyAppComponent

    override fun onCreate() {
        super.onCreate()
        testMyAppComponent = TestMyAppComponent(this)
    }

    override fun getAppComponent(): MyAppComponent {
        return testMyAppComponent
    }

    companion object {
        fun get(context: Context): TestMyApplication {
            return context.applicationContext as TestMyApplication
        }
    }
}