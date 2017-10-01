package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule {

    @Singleton
    @Provides
    fun dataManager(): BeansTypeDataManager {
        return mock()
    }
}