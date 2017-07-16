package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

class FragmentTestActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val fragment = FragmentTestUtil.fragment ?:
                throw RuntimeException(
                        "FragmentTestActivity requires FragmentTestUtil to have a non-null " +
                                "fragment.")
        return fragment
    }
}
