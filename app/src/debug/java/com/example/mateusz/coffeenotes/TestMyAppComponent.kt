package com.example.mateusz.coffeenotes.application

import android.content.Context
import com.example.mateusz.coffeenotes.BeansTypeFragment
import com.example.mateusz.coffeenotes.BeansTypeListFragment
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import org.mockito.Mockito

class TestMyAppComponent(context: Context): MyAppComponent(context) {
    val mockBeansTypeDataManager: BeansTypeDataManager =
            Mockito.mock(BeansTypeDataManager::class.java)

    override fun inject(beansTypeFragment: BeansTypeFragment) {
        beansTypeFragment.beansTypeDataManager = mockBeansTypeDataManager
    }

    override fun inject(beansTypeListFragment: BeansTypeListFragment) {
        beansTypeListFragment.beansTypeDataManager = mockBeansTypeDataManager
    }
}