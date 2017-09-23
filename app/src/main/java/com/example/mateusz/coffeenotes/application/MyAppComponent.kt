package com.example.mateusz.coffeenotes.application

import com.example.mateusz.coffeenotes.BeansTypeFragment
import com.example.mateusz.coffeenotes.BeansTypeListFragment
import com.example.mateusz.coffeenotes.CoffeeNoteFragment
import dagger.Component

@Component(modules = arrayOf(MyAppModule::class))
interface MyAppComponent {
    fun inject(beansTypeFragment: BeansTypeFragment)

    fun inject(beansTypeListFragment: BeansTypeListFragment)

    fun inject(coffeeNoteFragment: CoffeeNoteFragment)
}
