package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.application.MyApplication

class TestApplication : MyApplication() {
    override fun createGraph(): MyAppComponent {
        return DaggerTestComponent.builder().build()
    }
}