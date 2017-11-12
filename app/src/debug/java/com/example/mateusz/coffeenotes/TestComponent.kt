package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.application.DateModule
import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.database.DateHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestModule::class, DateModule::class))
interface TestComponent : MyAppComponent {
    fun dateHelper(): DateHelper
}