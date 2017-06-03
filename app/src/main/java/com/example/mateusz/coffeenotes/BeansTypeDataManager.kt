package com.example.mateusz.coffeenotes

import java.util.ArrayList
import java.util.UUID

class BeansTypeDataManager private constructor() {

    var beansTypeList: MutableList<BeansType> = ArrayList()

    init {
        for (i in 0..20) {
            val beansType = BeansType()
            beansType.name = "Type #" + Integer.toString(i)
            if (i % 2 == 0) {
                beansType.country = "Country #" + Integer.toString(i)
            }
            beansTypeList.add(beansType)
        }
    }

    fun getBeansTypeById(id: UUID): BeansType? {
        return beansTypeList.firstOrNull { it.id == id }
    }

    fun saveBeansType(beansType: BeansType) {
        if (getBeansTypeById(beansType.id) == null) {
            beansTypeList.add(beansType)
        }
    }

    object Holder {
        val INSTANCE = BeansTypeDataManager()
    }

    companion object {
        val instance: BeansTypeDataManager by lazy {
            Holder.INSTANCE
        }
    }
}
