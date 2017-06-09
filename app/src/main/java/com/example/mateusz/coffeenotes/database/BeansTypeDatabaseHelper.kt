package com.example.mateusz.coffeenotes.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mateusz.coffeenotes.database.BeansTypeDbSchema.*

class BeansTypeDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        val VERSION: Int = 1
        val DATABASE_NAME: String = "beansTypeBase.db"
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
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}