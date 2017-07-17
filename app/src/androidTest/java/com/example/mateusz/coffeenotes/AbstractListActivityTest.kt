package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matcher
import org.junit.Test

abstract class AbstractListActivityTest {

    @Test
    fun clickDeleteButton_inEditMode_removesItem() {
        onView(withId(R.id.menu_item_list_start_edit)).perform(click())

        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0, clickChildViewWithId(R.id.item_row_remove_button)))
    }

    companion object {
        fun clickChildViewWithId(resourceId: Int) : ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    return "Click child view with id $resourceId"
                }

                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun perform(uiController: UiController?, view: View) {
                    val childView = view.findViewById(resourceId)
                    childView.performClick()
                }

            }
        }
    }
}