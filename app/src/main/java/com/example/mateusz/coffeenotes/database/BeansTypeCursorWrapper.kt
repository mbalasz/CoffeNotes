package com.example.mateusz.coffeenotes.database

import android.database.Cursor
import android.database.CursorWrapper
import com.example.mateusz.coffeenotes.BeansType
import com.example.mateusz.coffeenotes.database.MainDbSchema.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID

class BeansTypeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getBeansType(dateTimeFormatter: DateTimeFormatter): BeansType {
        val uuid = UUID.fromString(getString(getColumnIndex(BeansTypeTable.Cols.UUID)))
        val name = getString(getColumnIndex(BeansTypeTable.Cols.NAME))
        val country = getString(getColumnIndex(BeansTypeTable.Cols.COUNTRY))
        val roastLevel = getInt(getColumnIndex(BeansTypeTable.Cols.ROAST_LEVEL))
        val date =
                LocalDate.parse(
                        getString(getColumnIndex(CoffeeNotesTable.Cols.DATE)), dateTimeFormatter)

        return BeansType(uuid, name, country, roastLevel, date = date)
    }
}