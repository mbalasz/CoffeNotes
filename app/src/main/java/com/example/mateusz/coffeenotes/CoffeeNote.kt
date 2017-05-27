package com.example.mateusz.coffeenotes

class CoffeeNote {
    private var coffeeType: CoffeeType? = null
    var beansType: BeansType? = null
    private var weight: Double = 0.toDouble()
    private var grinderSettings: Double = 0.toDouble()

    fun setCoffeeType(coffeeType: CoffeeType) {
        this.coffeeType = coffeeType
    }

    fun setWeight(weight: Double) {
        this.weight = weight
    }

    fun setGrinderSettings(grinderSettings: Double) {
        this.grinderSettings = grinderSettings
    }


    internal class Builder {
        private val coffeeNote: CoffeeNote = CoffeeNote()

        fun setCoffeeType(coffeeType: CoffeeType): Builder {
            coffeeNote.setCoffeeType(coffeeType)
            return this
        }

        fun setWeight(weight: Double): Builder {
            coffeeNote.setWeight(weight)
            return this
        }

        fun setGrinderSettings(grinderSettings: Double): Builder {
            coffeeNote.setGrinderSettings(grinderSettings)
            return this
        }
    }
}
