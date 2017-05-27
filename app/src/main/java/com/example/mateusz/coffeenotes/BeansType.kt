package com.example.mateusz.coffeenotes

import java.util.UUID

class BeansType {
    var id: UUID? = null
    var name: String? = null
    var country: String? = null

    init {
        id = UUID.randomUUID()
    }
}
