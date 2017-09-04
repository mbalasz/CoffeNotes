package com.example.mateusz.coffeenotes

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.mateusz.coffeenotes.EspressoUtil.Companion.clickView
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.database.BeansTypeDataManagerImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeansTypeListActivityUiTest {
    @Rule @JvmField
    val activityRule: ActivityTestRule<BeansTypeListActivity> =
            IntentsTestRule(BeansTypeListActivity::class.java)

    lateinit var beansTypeDataManager: BeansTypeDataManager

    @Before
    fun setUpBeansTypeList() {
        beansTypeDataManager =
                (activityRule.activity.application as MyApplication)
                        .getAppComponent()
                        .beansTypeDataManager
        // TODO: Provide a test module for data manager.
        beansTypeDataManager.saveBeansType(BeansType())
    }

    @After
    fun cleanUpBeansTypeList() {
        beansTypeDataManager.getBeansTypes().forEach {
            beansType -> beansTypeDataManager.removeBeansType(beansType)
        }
    }

    @Test
    fun onMenuNewItem_startBeansTypeActivity() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)

        intended(hasComponent(BeansTypeActivity::class.java.name))
    }

    @Test
    fun onBeansTypeFragmentResultOk_updateList() {
        val activityResult = Instrumentation.ActivityResult(RESULT_OK, null)
        intending(hasComponent(BeansTypeActivity::class.java.name)).respondWith(activityResult)

        clickView(R.id.menu_item_list_start_edit)
        clickView(R.id.menu_item_list_new_item)

    }
}