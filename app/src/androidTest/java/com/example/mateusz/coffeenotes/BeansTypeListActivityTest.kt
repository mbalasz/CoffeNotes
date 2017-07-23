package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeansTypeListActivityTest {

    @Rule @JvmField
    val activityRule: ActivityTestRule<BeansTypeListActivity> =
            IntentsTestRule(BeansTypeListActivity::class.java)

    @Before
    fun setUpBeansTypeList() {
        // TODO: Provide a test module for data manager.
        BeansTypeDataManager.instance(activityRule.activity).saveBeansType(BeansType())
    }

    @Test
    fun onMenuNewItem_startBeansTypeActivity() {
        onView(withId(R.id.menu_item_list_start_edit)).perform(click())

        onView(withId(R.id.menu_item_list_new_item)).perform(click())

        intended(IntentMatchers.hasComponent(BeansTypeActivity::class.java.name))
    }
}