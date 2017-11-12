package com.example.mateusz.coffeenotes

import java.util.*

data class BeansType(
    val id: UUID = UUID.randomUUID(),
    var name: String? = null,
    var country: String? = null,
    var roastLevel: Int = 1,
    var photoFileName: String = "IMG_$id.jpg",
    var date: Date = Calendar.getInstance().time
)
