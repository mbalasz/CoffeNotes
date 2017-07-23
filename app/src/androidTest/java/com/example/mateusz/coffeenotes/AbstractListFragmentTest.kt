package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers.*

abstract class AbstractListFragmentTest {
    protected fun assertEditModeIsDisplayed(isDisplayed: Boolean) {
        if (isDisplayed) {
            assertViewIsDisplayed(R.id.menu_item_list_finish_edit)
            assertViewIsDisplayed(R.id.menu_item_list_new_item)
        } else {
            assertViewDoesNotExist(R.id.menu_item_list_finish_edit)
            assertViewDoesNotExist(R.id.menu_item_list_new_item)
        }
    }

    protected fun assertViewDoesNotExist(viewId: Int) {
        onView(withId(viewId)).check(doesNotExist())
    }

    protected fun assertViewIsDisplayed(viewId: Int) {
        onView(withId(viewId)).check(matches(isDisplayed()))
    }

    protected fun clickView(viewId: Int) {
        onView(withId(viewId)).perform(click())
    }
}