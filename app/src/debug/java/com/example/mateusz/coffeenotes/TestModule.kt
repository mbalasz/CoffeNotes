package com.example.mateusz.coffeenotes

import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestModule {

    @Singleton
    @Provides
    fun dataManager(): BeansTypeDataManager {
        return Mockito.mock(BeansTypeDataManager::class.java)
    }
}