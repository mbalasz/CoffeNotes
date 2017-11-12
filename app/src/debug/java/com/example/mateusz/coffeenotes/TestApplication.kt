package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.application.MyApplication

class TestApplication : MyApplication() {
    override fun createGraph(): MyAppComponent {
        return DaggerTestComponent.builder().build()
    }

    // Can't init AndroidThreeTen due to bug in robolectric.
    // https://github.com/JakeWharton/ThreeTenABP/issues/14#issuecomment-255417720.
    override fun initAndroidThreeTen() {}
}