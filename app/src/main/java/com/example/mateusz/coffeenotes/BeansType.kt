package com.example.mateusz.coffeenotes

import java.util.UUID

data class BeansType(
    val id: UUID = UUID.randomUUID(),
    var name: String? = null,
    var country: String? = null,
    var roastLevel: Int = 1
)
