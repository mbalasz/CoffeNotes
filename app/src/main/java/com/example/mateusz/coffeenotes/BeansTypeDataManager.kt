package com.example.mateusz.coffeenotes

import java.util.ArrayList
import java.util.UUID

class BeansTypeDataManager private constructor() {

    var beansTypeList: MutableList<BeansType> = ArrayList()

    init {
        for (i in 0..20) {
            val beansType = BeansType()
            beansType.name = "Type #" + Integer.toString(i)
            beansType.country = "Country #" + Integer.toString(i)
            beansTypeList.add(beansType)
        }
    }

    fun getBeansTypeById(id: UUID): BeansType? {
        return beansTypeList.firstOrNull { it.id == id }
    }

    fun createBeansType(): BeansType {
        val newBeansType = BeansType()
        beansTypeList.add(newBeansType)
        return newBeansType
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