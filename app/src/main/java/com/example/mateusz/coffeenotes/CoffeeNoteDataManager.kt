package com.example.mateusz.coffeenotes

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mateusz.coffeenotes.database.CoffeeNotesCursorWrapper
import com.example.mateusz.coffeenotes.database.MainDatabaseHelper
import com.example.mateusz.coffeenotes.database.MainDbSchema
import java.util.ArrayList

class CoffeeNoteDataManager private constructor(context: Context) {
    // TODO: make this and BeansTypeDataManagerImpl a subclass of an abstract DataManager.
    val dataBase: SQLiteDatabase = MainDatabaseHelper(context).writableDatabase

    fun getCoffeeNotes(): List<CoffeeNote> {
        val coffeeNotes: MutableList<CoffeeNote> = ArrayList()
        val cursor = queryCoffeeNotes(null, null)

        cursor.use {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                coffeeNotes.add(cursor.getCoffeeNote())
                cursor.moveToNext()
            }
        }

        return coffeeNotes.toList()
    }

    private fun queryCoffeeNotes(whereClause: String?, whereArgs: Array<String>?):
            CoffeeNotesCursorWrapper {
        val cursor = query(MainDbSchema.CoffeeNotesTable.NAME, whereClause, whereArgs)
        return CoffeeNotesCursorWrapper(cursor)
    }

    private fun query(table: String, whereClause: String?, whereArgs: Array<String>?): Cursor {
        return dataBase.query(
                table,
                null,
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null, // orderBy
                null // limit
        )
    }

    companion object {
        private lateinit var context: Context
        private val instance: CoffeeNoteDataManager by lazy {
            CoffeeNoteDataManager(context)
        }

        fun instance(context: Context): CoffeeNoteDataManager {
            this.context = context
            return instance
        }
    }
}