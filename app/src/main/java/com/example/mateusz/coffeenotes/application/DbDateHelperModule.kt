package com.example.mateusz.coffeenotes.application

import dagger.Module
import dagger.Provides
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
class DbDateHelperModule {
    @Singleton
    @Provides
    fun dateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
}