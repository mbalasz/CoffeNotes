package com.example.mateusz.coffeenotes.database

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateHelper @Inject constructor(var dateFormat: SimpleDateFormat) {
    fun dateToString(date: Date): String {
//        return dateFormat.format(date)
        return String()
    }

    fun stringToDate(date: String): Date {
//        return dateFormat.parse(date)
        return Date()
    }
}