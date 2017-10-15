package com.example.mateusz.coffeenotes.application

import com.example.mateusz.coffeenotes.BeansTypeFragment
import com.example.mateusz.coffeenotes.BeansTypeListFragment
import com.example.mateusz.coffeenotes.CoffeeNoteFragment
import com.example.mateusz.coffeenotes.CoffeeNoteListFragment
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MyAppModule::class))
interface MyAppComponent {
    fun inject(beansTypeFragment: BeansTypeFragment)

    fun inject(beansTypeListFragment: BeansTypeListFragment)

    fun inject(coffeeNoteFragment: CoffeeNoteFragment)

    fun inject(coffeeNoteListFragment: CoffeeNoteListFragment)

    fun dataManager(): BeansTypeDataManager
}
