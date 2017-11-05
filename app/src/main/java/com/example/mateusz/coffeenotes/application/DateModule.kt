package com.example.mateusz.coffeenotes.application

import com.example.mateusz.coffeenotes.database.DateHelper
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
class DateModule {
    @Singleton
    @Provides
    fun dateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
}