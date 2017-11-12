package com.example.mateusz.coffeenotes.database

import android.database.Cursor
import android.database.CursorWrapper
import com.example.mateusz.coffeenotes.CoffeeNote
import com.example.mateusz.coffeenotes.database.MainDbSchema.CoffeeNotesTable
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID

class CoffeeNotesCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getCoffeeNote(dateTimeFormatter: DateTimeFormatter): CoffeeNote {
        val uuid = UUID.fromString(getString(getColumnIndex(CoffeeNotesTable.Cols.UUID)))
        val title = getString(getColumnIndex(CoffeeNotesTable.Cols.TITLE))
        var beansTypeId: UUID? = null
        getString(getColumnIndex(CoffeeNotesTable.Cols.BEANS_TYPE_ID))?.let {
            beansTypeId = UUID.fromString(it)
        }
        val date =
                LocalDate.parse(
                        getString(getColumnIndex(CoffeeNotesTable.Cols.DATE)), dateTimeFormatter)

        return CoffeeNote(uuid = uuid, title = title, beansTypeId = beansTypeId, date = date)
    }
}