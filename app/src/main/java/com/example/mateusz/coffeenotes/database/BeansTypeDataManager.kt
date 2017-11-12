package com.example.mateusz.coffeenotes.database

import com.example.mateusz.coffeenotes.BeansType
import com.example.mateusz.coffeenotes.CoffeeNote
import java.io.File
import java.util.UUID

interface BeansTypeDataManager {
    fun getCoffeeNotes(): List<CoffeeNote>

    fun getCoffeeNoteById(id: UUID): CoffeeNote?

    fun saveCoffeeNote(coffeeNote: CoffeeNote)

    fun removeCoffeeNote(coffeeNote: CoffeeNote)

    fun getBeansTypes(): List<BeansType>

    fun getBeansTypeById(id: UUID): BeansType?

    fun saveBeansType(beansType: BeansType)

    fun removeBeansType(beansType: BeansType)

    fun getPhotoFile(beansType: BeansType): File
}