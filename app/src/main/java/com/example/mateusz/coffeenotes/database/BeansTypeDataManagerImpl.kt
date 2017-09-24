package com.example.mateusz.coffeenotes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import com.example.mateusz.coffeenotes.BeansType
import com.example.mateusz.coffeenotes.database.MainDbSchema.*
import java.io.File
import java.util.ArrayList
import java.util.UUID

class BeansTypeDataManagerImpl(private val context: Context, dbHelper: SQLiteOpenHelper):
        BeansTypeDataManager {
    private val dataBase: SQLiteDatabase = dbHelper.writableDatabase
    override fun getBeansTypes(): List<BeansType> {
        val beansTypes: MutableList<BeansType> = ArrayList()
        val cursor = queryBeansTypes(null, null)

        cursor.use {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                beansTypes.add(cursor.getBeansType())
                cursor.moveToNext()
            }
        }

        return beansTypes.toList()
    }

    override fun getBeansTypeById(id: UUID): BeansType? {
        val cursor = queryBeansTypes(
                "${BeansTypeTable.Cols.UUID} = ?",
                arrayOf(id.toString()))

        cursor.use {
            if (cursor.moveToFirst()) {
                return cursor.getBeansType()
            } else {
                return null
            }
        }
    }

    override fun saveBeansType(beansType: BeansType) {
        if (getBeansTypeById(beansType.id) == null) {
            addBeansType(beansType)
        } else {
            updateBeansType(beansType)
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

    private fun addBeansType(beansType: BeansType) {
        val contentValues = getContentValues(beansType)

        dataBase.insert(BeansTypeTable.NAME, null, contentValues)
    }

    private fun updateBeansType(beansType: BeansType) {
        val uuid = beansType.id.toString()
        val contentValues = getContentValues(beansType)

        dataBase.update(
                BeansTypeTable.NAME,
                contentValues,
                "${BeansTypeTable.Cols.UUID} = ?",
                arrayOf(uuid))
    }

    private fun queryBeansTypes(whereClause: String?, whereArgs: Array<String>?):
            BeansTypeCursorWrapper {
        val cursor = query(BeansTypeTable.NAME, whereClause, whereArgs)
        return BeansTypeCursorWrapper(cursor)
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
        return contentValues
    }
}
