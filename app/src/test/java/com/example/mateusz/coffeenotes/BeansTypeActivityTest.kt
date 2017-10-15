package com.example.mateusz.coffeenotes

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class BeansTypeActivityTest {

    lateinit var beansTypeDataManager: BeansTypeDataManager
    val appComponent = (RuntimeEnvironment.application as MyApplication).getAppComponent()

    @Before
    fun setUp() {
        beansTypeDataManager = appComponent.dataManager()
    }

    @Test
    fun saveButton_savesDataInManager() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = BeansType(name = "Test name", country = "Test country", roastLevel = 1)

        fillData(activity, testBeansType)
        ShadowView.clickOn(activity.findViewById(R.id.menu_item_save))

        argumentCaptor<BeansType>().apply {
            verify(beansTypeDataManager).saveBeansType(capture())
            assertThat(
                    firstValue.copy(
                            id = testBeansType.id,
                            photoFileName = testBeansType.photoFileName))
                    .isEqualTo(testBeansType)
        }
    }

    @Test
    fun discardButton_discardsChanges() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = BeansType(name = "Test name", country = "Test country", roastLevel = 1)

        fillData(activity, testBeansType)

        ShadowView.clickOn(activity.findViewById(R.id.menu_item_discard))

        verify(beansTypeDataManager, never()).saveBeansType(any())
    }

    @Test
    fun backButton_discardsChanges() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = BeansType(name = "Test name", country = "Test country", roastLevel = 1)

        fillData(activity, testBeansType)

        activity.onBackPressed()

        verify(beansTypeDataManager, never()).saveBeansType(any())
    }

    private fun fillData(activity: Activity, beansType: BeansType) {
        (activity.findViewById(R.id.beans_name_edit_text) as EditText).setText(beansType.name)
        (activity.findViewById(R.id.beans_country_edit_text) as EditText)
                .setText(beansType.country)
        (activity.findViewById(R.id.roast_level_button) as Button).text =
                beansType.roastLevel.toString()
    }
}