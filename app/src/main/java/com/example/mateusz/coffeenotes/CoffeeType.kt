package com.example.mateusz.coffeenotes

enum class CoffeeType private constructor(private val coffeeType: String) {
    ESPRESSO("Espresso"),
    LATTE("Latte"),
    FLAT_WHITE("Flat white");

    override fun toString(): String {
        return coffeeType
    }
}
