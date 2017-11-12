package com.example.mateusz.coffeenotes

import org.threeten.bp.LocalDate
import java.util.UUID

data class BeansType(
    val id: UUID = UUID.randomUUID(),
    var name: String? = null,
    var country: String? = null,
    var roastLevel: Int = 1,
    var photoFileName: String = "IMG_$id.jpg",
    var date: LocalDate = LocalDate.now()
)
