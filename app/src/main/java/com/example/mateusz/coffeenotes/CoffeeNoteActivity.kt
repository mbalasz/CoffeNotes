package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

class CoffeeNoteActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CoffeeNoteFragment.newInstance()
    }
}
