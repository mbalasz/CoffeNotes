package com.example.mateusz.coffeenotes

import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoffeeNoteListActivityTest : AbstractListFragmentTest() {

    @Rule @JvmField
    val activityRule: ActivityTestRule<CoffeeNoteListActivity> =
            IntentsTestRule(CoffeeNoteListActivity::class.java)

    @Test
    fun onMenuNewItem_startCoffeeNoteActivity() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)

        intended(IntentMatchers.hasComponent(CoffeeNoteActivity::class.java.name))
    }
}