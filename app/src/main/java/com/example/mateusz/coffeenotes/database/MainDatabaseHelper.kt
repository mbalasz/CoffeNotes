package com.example.mateusz.coffeenotes.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mateusz.coffeenotes.database.MainDbSchema.BeansTypeTable
import com.example.mateusz.coffeenotes.database.MainDbSchema.CoffeeNotesTable

class MainDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        val VERSION: Int = 1
        val DATABASE_NAME: String = "coffeeNotes.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
                """
                create table ${BeansTypeTable.NAME}(
                ${BeansTypeTable.Cols.UUID},
                ${BeansTypeTable.Cols.NAME},
                ${BeansTypeTable.Cols.COUNTRY},
                ${BeansTypeTable.Cols.ROAST_LEVEL}
                )
                """
        )

        db!!.execSQL(
                """
                create table ${CoffeeNotesTable.NAME}(
                ${CoffeeNotesTable.Cols.UUID},
                ${CoffeeNotesTable.Cols.TITLE},
                ${CoffeeNotesTable.Cols.BEANS_TYPE_ID}
                ${CoffeeNotesTable.Cols.DATE}
                )
                """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}