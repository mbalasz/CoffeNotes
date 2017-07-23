package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.example.mateusz.coffeenotes.view.ContentViewHolder
import com.example.mateusz.coffeenotes.view.EditableListFragment
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditableListFragmentTest {

    @Rule @JvmField
    val activityRule = FragmentTestRule(EditableListFragmentForTest::class.java)

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

    @Test
    fun clickDeleteButton_inEditMode_removesItem() {
        onView(withId(R.id.menu_item_list_start_edit)).perform(click())

        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0, clickChildViewWithId(R.id.item_row_remove_button)))
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

    class EditableListFragmentForTest : EditableListFragment<Int>() {

        override fun getDataList(): List<Int> {
            return listOf(1, 2, 3, 4)
        }

        override fun createContentViewHolder(): ContentViewHolder<Int> {
            return object : ContentViewHolder<Int>() {
                private val button: Button = Button(context)
                init {
                    view = button
                }

                override fun onClicked() {}

                override fun onUpdateView() {
                    button.text = data.toString()
                }

                override fun onRecycle() {}
            }
        }

        override fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<Int>) {}
    }
}