package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId

class EspressoUtil private constructor() {
    companion object {
        fun clickView(viewId: Int) {
            onView(withId(viewId)).perform(click())
        }

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }

    }
}