package com.example.mateusz.coffeenotes

import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment

class FragmentTestRule<T: Fragment>(val fragmentClass: Class<T>)
    : ActivityTestRule<FragmentTestActivity>(FragmentTestActivity::class.java) {
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        FragmentTestActivity.fragment = fragmentClass.newInstance()
    }
}