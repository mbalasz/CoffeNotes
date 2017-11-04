package com.example.mateusz.coffeenotes.database

class MainDbSchema {
    object BeansTypeTable {
        val NAME: String = "beans_types"

        object Cols {
            val UUID: String = "uuid"
            val NAME: String = "name"
            val COUNTRY: String = "country"
            val ROAST_LEVEL: String = "roast_level"
        }
    }

    object CoffeeNotesTable {
        val NAME: String = "coffee_notes"

        object Cols {
            val UUID: String = "uuid"
            val TITLE: String = "title"
            val BEANS_TYPE_ID: String = "beans_type_id"
            val DATE: String = "date"
        }
    }
}