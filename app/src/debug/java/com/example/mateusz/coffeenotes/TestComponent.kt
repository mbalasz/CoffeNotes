package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.application.DateModule
import com.example.mateusz.coffeenotes.application.MyAppComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestModule::class, DateModule::class))
interface TestComponent : MyAppComponent