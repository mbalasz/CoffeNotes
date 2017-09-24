package com.example.mateusz.coffeenotes.application

import android.content.Context
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.database.BeansTypeDataManagerImpl
import com.example.mateusz.coffeenotes.database.MainDatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MyAppModule(private val context: Context) {

    @Singleton
    @Provides
    fun dataManager(): BeansTypeDataManager {
        return BeansTypeDataManagerImpl(context, MainDatabaseHelper(context))
    }
}