package com.example.mateusz.coffeenotes

import org.threeten.bp.LocalDate
import java.util.UUID

data class CoffeeNote(
    val uuid: UUID = UUID.randomUUID(),
    var title: String = "",
    var coffeeType: CoffeeType? = null,
    var beansTypeId: UUID? = null,
    var weight: Double = 0.0,
    var grinderSettings: Double = 0.0,
    var date: LocalDate = LocalDate.now()
)