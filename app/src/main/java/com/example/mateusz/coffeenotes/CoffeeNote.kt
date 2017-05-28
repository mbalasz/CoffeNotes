package com.example.mateusz.coffeenotes

data class CoffeeNote(var coffeeType: CoffeeType? = null,
                      var beansType: BeansType? = null,
                      var weight: Double = 0.0,
                      var grinderSettings: Double = 0.0)