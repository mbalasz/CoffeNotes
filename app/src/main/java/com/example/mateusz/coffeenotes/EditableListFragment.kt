package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

open class EditableListFragment : Fragment() {
    protected lateinit var menu: Menu
    protected var isInEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.list_menu, menu)
        this.menu = menu!!
    }

    open protected fun setEditMode(enabled: Boolean) {
        isInEditMode = enabled
        menu.findItem(R.id.menu_item_list_start_edit).isVisible = !enabled
        menu.findItem(R.id.menu_item_list_finish_edit).isVisible = enabled
        menu.findItem(R.id.menu_item_list_new_item).isVisible = enabled
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_list_start_edit -> {
                setEditMode(true)
                return true
            }
            R.id.menu_item_list_finish_edit -> {
                setEditMode(false)
                return true
            }
            R.id.menu_item_list_new_item -> {
                setEditMode(false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}