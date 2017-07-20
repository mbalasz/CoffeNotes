package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.example.mateusz.coffeenotes.view.EditableListFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditableListFragmentTest {

    @Rule @JvmField
    val activityRule = FragmentTestRule(EditableListFragment::class.java)

    @Test
    fun editModeItemsHiddenByDefault() {
        assertEditModeIsDisplayed(false)
    }

    @Test
    fun clickEditMenuItem_TurnsOnEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        assertEditModeIsDisplayed(true)
    }

    @Test
    fun clickFinishEditItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_finish_edit)
        assertEditModeIsDisplayed(false)
    }

    @Test
    fun clickAddNewItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)
        assertEditModeIsDisplayed(false)
    }

    private fun assertEditModeIsDisplayed(isDisplayed: Boolean) {
        if (isDisplayed) {
            assertViewIsDisplayed(R.id.menu_item_list_finish_edit)
            assertViewIsDisplayed(R.id.menu_item_list_new_item)
        } else {
            assertViewDoesNotExist(R.id.menu_item_list_finish_edit)
            assertViewDoesNotExist(R.id.menu_item_list_new_item)
        }
    }

    private fun assertViewDoesNotExist(viewId: Int) {
        onView(withId(viewId)).check(doesNotExist())
    }

    private fun assertViewIsDisplayed(viewId: Int) {
        onView(withId(viewId)).check(matches(isDisplayed()))
    }

    private fun clickView(viewId: Int) {
        onView(withId(viewId)).perform(click())
    }
}