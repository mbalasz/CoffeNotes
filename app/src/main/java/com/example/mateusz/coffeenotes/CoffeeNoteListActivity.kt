package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

class CoffeeNoteListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CoffeeNoteListFragment.newInstance()
    }
}