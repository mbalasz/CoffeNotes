package com.example.mateusz.coffeenotes

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.times
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.mateusz.coffeenotes.EspressoUtil.Companion.clickView
import com.example.mateusz.coffeenotes.EspressoUtil.Companion.withRecyclerView
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeansTypeListActivityUiTest {
    @Rule @JvmField
    val activityRule: ActivityTestRule<BeansTypeListActivity> =
            IntentsTestRule(BeansTypeListActivity::class.java)
    val beansTypeDataManager: BeansTypeDataManager by lazy {
        (activityRule.activity.application as MyApplication).getAppComponent().dataManager()
    }

    @After
    fun clearBeansTypeDatabase() {
        for (beansType in beansTypeDataManager.getBeansTypes()) {
            beansTypeDataManager.removeBeansType(beansType)
        }
    }

    @Test
    fun onMenuNewItem_startBeansTypeActivity() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)

        intended(hasComponent(BeansTypeActivity::class.java.name))
    }

    @Test
    fun addNewBeansType() {
        assertBeansTypeListCount(0)
        val myBeansText = "My beans"

        clickView(R.id.menu_item_list_start_edit)
        clickView(R.id.menu_item_list_new_item)

        onView(withId(R.id.beans_name_edit_text)).perform(typeText(myBeansText))
        clickView(R.id.menu_item_save)

        assertBeansTypeListCount(1)
        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(ViewAssertions.matches(hasDescendant(withText(myBeansText))))
    }

    @Test
    fun removeBeansType() {
        val myBeansText = "My beans"

        clickView(R.id.menu_item_list_start_edit)
        clickView(R.id.menu_item_list_new_item)

        onView(withId(R.id.beans_name_edit_text)).perform(typeText(myBeansText))
        clickView(R.id.menu_item_save)

        clickView(R.id.menu_item_list_start_edit)
        onView(withRecyclerView(R.id.recycler_view)
                .atPositionOnView(0, R.id.item_row_remove_button)).perform(click())

        assertBeansTypeListCount(0)
    }

    @Test
    fun editBeansType() {
        val myBeansText = "My beans"

        clickView(R.id.menu_item_list_start_edit)
        clickView(R.id.menu_item_list_new_item)
        intended(hasComponent(BeansTypeActivity::class.java.name))

        onView(withId(R.id.beans_name_edit_text)).perform(typeText(myBeansText))
        clickView(R.id.menu_item_save)

        clickView(R.id.menu_item_list_start_edit)
        onView(withRecyclerView(R.id.recycler_view).atPosition(0)).perform(click())

        intended(hasComponent(BeansTypeActivity::class.java.name), times(2))
    }

    private fun assertBeansTypeListCount(count: Int) {
        val beansTypeList = activityRule.activity.findViewById(R.id.recycler_view) as RecyclerView
        assertThat(beansTypeList.adapter.itemCount, `is`(count))
    }
}