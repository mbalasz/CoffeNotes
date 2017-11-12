package com.example.mateusz.coffeenotes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import com.example.mateusz.coffeenotes.BeansType
import com.example.mateusz.coffeenotes.CoffeeNote
import com.example.mateusz.coffeenotes.database.MainDbSchema.BeansTypeTable
import com.example.mateusz.coffeenotes.database.MainDbSchema.CoffeeNotesTable
import java.io.File
import java.util.*

class BeansTypeDataManagerImpl(
        private val context: Context,
        dbHelper: SQLiteOpenHelper,
        private val dateHelper: DateHelper): BeansTypeDataManager {
    private val dataBase: SQLiteDatabase = dbHelper.writableDatabase

    override fun getCoffeeNotes(): List<CoffeeNote> {
        val cursor = queryCoffeeNote(null, null)

        return generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { cursor.getCoffeeNote(dateHelper)}
                .toList()
    }

    override fun getCoffeeNoteById(id: UUID): CoffeeNote? {
        val cursor = queryCoffeeNote("${CoffeeNotesTable.Cols.UUID} = ?", arrayOf(id.toString()))

        cursor.use {
            if (cursor.moveToFirst()) return cursor.getCoffeeNote(dateHelper) else return null
        }
    }

    override fun saveCoffeeNote(coffeeNote: CoffeeNote) {
        if (getCoffeeNoteById(coffeeNote.uuid) == null) {
            addCoffeeNote(coffeeNote)
        } else {
            updateContentValues(
                    CoffeeNotesTable.NAME,
                    getContentValues(coffeeNote),
                    "${CoffeeNotesTable.Cols.UUID} = ?",
                    arrayOf(coffeeNote.uuid.toString()))
        }
    }

    override fun removeCoffeeNote(coffeeNote: CoffeeNote) {
        dataBase.delete(
                CoffeeNotesTable.NAME,
                "${CoffeeNotesTable.Cols.UUID} = ?",
                arrayOf(coffeeNote.uuid.toString()))
    }

    override fun getBeansTypes(): List<BeansType> {
        val cursor = queryBeansTypes(null, null)

        return generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { cursor.getBeansType(dateHelper)}
                .toList()
    }

    override fun getBeansTypeById(id: UUID): BeansType? {
        val cursor = queryBeansTypes(
                "${BeansTypeTable.Cols.UUID} = ?",
                arrayOf(id.toString()))

        cursor.use {
            if (cursor.moveToFirst()) return cursor.getBeansType(dateHelper) else return null
        }
    }

    override fun saveBeansType(beansType: BeansType) {
        if (getBeansTypeById(beansType.id) == null) {
            addBeansType(beansType)
        } else {
            updateContentValues(
                    BeansTypeTable.NAME,
                    getContentValues(beansType),
                    "${BeansTypeTable.Cols.UUID} = ?",
                    arrayOf(beansType.id.toString()))
        }
    }

    override fun removeBeansType(beansType: BeansType) {
        dataBase.delete(
                BeansTypeTable.NAME,
                "${BeansTypeTable.Cols.UUID} = ?",
                arrayOf(beansType.id.toString()))
    }

    override fun getPhotoFile(beansType: BeansType): File {
        return File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                beansType.photoFileName)
    }

    private fun addCoffeeNote(coffeeNote: CoffeeNote) {
        val contentValues = getContentValues(coffeeNote)

        addContentValues(CoffeeNotesTable.NAME, contentValues)
    }

    private fun addBeansType(beansType: BeansType) {
        val contentValues = getContentValues(beansType)

        addContentValues(BeansTypeTable.NAME, contentValues)
    }

    private fun addContentValues(tableName: String, contentValues: ContentValues) {
        dataBase.insert(tableName, null, contentValues)
    }

    private fun updateContentValues(
            tableName: String,
            contentValues: ContentValues,
            whereClause: String?,
            whereArgs: Array<String>?) {
        dataBase.update(tableName, contentValues, whereClause, whereArgs)
    }

    private fun queryBeansTypes(whereClause: String?, whereArgs: Array<String>?):
            BeansTypeCursorWrapper {
        val cursor = query(BeansTypeTable.NAME, whereClause, whereArgs)
        return BeansTypeCursorWrapper(cursor)
    }

    private fun queryCoffeeNote(whereClause: String?, whereArgs: Array<String>?):
            CoffeeNotesCursorWrapper {
        val cursor = query(CoffeeNotesTable.NAME, whereClause, whereArgs)
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

    private fun getContentValues(beansType: BeansType): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(BeansTypeTable.Cols.UUID, beansType.id.toString())
        contentValues.put(BeansTypeTable.Cols.NAME, beansType.name)
        contentValues.put(BeansTypeTable.Cols.COUNTRY, beansType.country)
        contentValues.put(BeansTypeTable.Cols.ROAST_LEVEL, beansType.roastLevel)
        contentValues.put(BeansTypeTable.Cols.DATE, dateHelper.dateToString(beansType.date))
        return contentValues
    }

    private fun getContentValues(coffeeNote: CoffeeNote): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(CoffeeNotesTable.Cols.UUID, coffeeNote.uuid.toString())
        contentValues.put(CoffeeNotesTable.Cols.TITLE, coffeeNote.title)
        coffeeNote.beansTypeId?.let {
            contentValues.put(CoffeeNotesTable.Cols.BEANS_TYPE_ID, it.toString())
        }
        contentValues.put(CoffeeNotesTable.Cols.DATE, dateHelper.dateToString(coffeeNote.date))
        return contentValues
    }
}
