package com.example.mateusz.coffeenotes.application

import android.content.Context
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.database.BeansTypeDataManagerImpl
import com.example.mateusz.coffeenotes.database.DateHelper
import com.example.mateusz.coffeenotes.database.MainDatabaseHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
class MyAppModule(private val context: Context) {
    @Singleton
    @Provides
    fun dataManager(dateHelper: DateHelper): BeansTypeDataManager {
        return BeansTypeDataManagerImpl(context, MainDatabaseHelper(context), dateHelper)
    }
}