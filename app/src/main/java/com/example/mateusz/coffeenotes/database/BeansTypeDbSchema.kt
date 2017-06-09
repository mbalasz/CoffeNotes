package com.example.mateusz.coffeenotes.database

class BeansTypeDbSchema {
    object BeansTypeTable {
        val NAME: String = "beans_types"

        object Cols {
            val UUID: String = "uuid"
            val NAME: String = "name"
            val COUNTRY: String = "country"
            val ROAST_LEVEL: String = "roast_level"
        }
    }
}