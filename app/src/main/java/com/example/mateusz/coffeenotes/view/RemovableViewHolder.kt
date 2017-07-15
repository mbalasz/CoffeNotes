package com.example.mateusz.coffeenotes.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.example.mateusz.coffeenotes.R

abstract class RemovableViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val removeButton: ImageButton =
            itemView.findViewById(R.id.item_row_remove_button) as ImageButton

    init {
        removeButton.setOnClickListener {
            onRemoveItem()
        }
    }

    fun updateEditMode(isInEditMode: Boolean) {
        removeButton.visibility = if (isInEditMode) View.VISIBLE else View.INVISIBLE
    }

    abstract protected fun onRemoveItem()
}