package com.example.mateusz.coffeenotes

import java.util.*

data class CoffeeNote(
        val uuid: UUID = UUID.randomUUID(),
        var title: String = "",
        var coffeeType: CoffeeType? = null,
        var beansType: BeansType? = null,
        var weight: Double = 0.0,
        var grinderSettings: Double = 0.0)