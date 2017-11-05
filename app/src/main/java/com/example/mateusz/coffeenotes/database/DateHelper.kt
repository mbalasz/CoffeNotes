package com.example.mateusz.coffeenotes.database

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateHelper @Inject constructor(private val dateFormat: SimpleDateFormat) {
    fun dateToString(date: Date): String {
        return dateFormat.format(date)
    }

    fun stringToDate(date: String): Date {
        return dateFormat.parse(date)
    }
}