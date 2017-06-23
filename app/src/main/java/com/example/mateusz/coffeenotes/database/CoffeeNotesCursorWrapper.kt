package com.example.mateusz.coffeenotes.database

import android.database.Cursor
import android.database.CursorWrapper
import com.example.mateusz.coffeenotes.CoffeeNote
import com.example.mateusz.coffeenotes.database.MainDbSchema.*
import java.util.*

class CoffeeNotesCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getCoffeeNote(): CoffeeNote {
        val uuid = UUID.fromString(getString(getColumnIndex(CoffeeNotesTable.Cols.UUID)))
        val title = getString(getColumnIndex(CoffeeNotesTable.Cols.TITLE))

        return CoffeeNote(uuid, title)
    }
}