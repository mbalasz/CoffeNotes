package com.example.mateusz.coffeenotes.application

import android.content.Context
import com.example.mateusz.coffeenotes.BeansTypeFragment
import com.example.mateusz.coffeenotes.BeansTypeListFragment
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.database.BeansTypeDataManagerImpl
import com.example.mateusz.coffeenotes.database.MainDatabaseHelper

open class MyAppComponent(private val context: Context) {
    val beansTypeDataManager: BeansTypeDataManager by lazy {
        BeansTypeDataManagerImpl(context, MainDatabaseHelper(context))
    }

    open fun inject(beansTypeFragment: BeansTypeFragment) {
        beansTypeFragment.beansTypeDataManager = beansTypeDataManager
    }

    open fun inject(beansTypeListFragment: BeansTypeListFragment) {
        beansTypeListFragment.beansTypeDataManager = beansTypeDataManager
    }
}