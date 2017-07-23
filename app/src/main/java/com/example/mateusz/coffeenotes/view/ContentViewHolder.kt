package com.example.mateusz.coffeenotes.view

import android.view.View

abstract class ContentViewHolder<T: Any> {
    lateinit var view: View
    lateinit var data: T

    fun bindData(data: T) {
        this.data = data
        onUpdateView()
    }

    abstract fun onClicked()

    abstract fun onUpdateView()

    abstract fun onRecycle()
}