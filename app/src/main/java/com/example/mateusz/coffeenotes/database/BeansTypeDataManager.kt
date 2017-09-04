package com.example.mateusz.coffeenotes.database

import com.example.mateusz.coffeenotes.BeansType
import java.io.File
import java.util.*

interface BeansTypeDataManager {
    fun getBeansTypes(): List<BeansType>

    fun getBeansTypeById(id: UUID): BeansType?

    fun saveBeansType(beansType: BeansType)

    fun removeBeansType(beansType: BeansType)

    fun getPhotoFile(beansType: BeansType): File
}