package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

class EditableListFragmentTestActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return EditableListFragment()
    }
}
