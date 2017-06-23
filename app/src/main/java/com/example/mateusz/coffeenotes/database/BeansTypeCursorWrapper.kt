package com.example.mateusz.coffeenotes.database

import android.database.Cursor
import android.database.CursorWrapper
import com.example.mateusz.coffeenotes.BeansType
import com.example.mateusz.coffeenotes.database.MainDbSchema.*
import java.util.*

class BeansTypeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getBeansType(): BeansType {
        val uuid = UUID.fromString(getString(getColumnIndex(BeansTypeTable.Cols.UUID)))
        val name = getString(getColumnIndex(BeansTypeTable.Cols.NAME))
        val country = getString(getColumnIndex(BeansTypeTable.Cols.COUNTRY))
        val roastLevel = getInt(getColumnIndex(BeansTypeTable.Cols.ROAST_LEVEL))

        return BeansType(uuid, name, country, roastLevel)
    }
}