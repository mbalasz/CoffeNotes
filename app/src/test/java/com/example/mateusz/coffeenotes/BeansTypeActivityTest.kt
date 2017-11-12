package com.example.mateusz.coffeenotes

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.database.DateHelper
import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView
import android.app.DatePickerDialog
import org.robolectric.shadows.ShadowDatePickerDialog
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class BeansTypeActivityTest {

    private lateinit var beansTypeDataManager: BeansTypeDataManager
    private lateinit var dateHelper: DateHelper
    private val appComponent = (RuntimeEnvironment.application as MyApplication).getAppComponent() as TestComponent

    @Before
    fun setUp() {
        beansTypeDataManager = appComponent.dataManager()
        dateHelper = appComponent.dateHelper()
    }

    @Test
    fun initState_prefillFields() {
        val uuid = UUID.randomUUID()
        val name = "Test name"
        val country = "Test country"
        val roastLevel = 4
        val date = Calendar.getInstance().time
        val testBeansType = BeansType(uuid, name, country, roastLevel, date = date)
        //TODO: Migrate to https://github.com/JakeWharton/ThreeTenABP
        whenever(beansTypeDataManager.getBeansTypeById(uuid)).thenReturn(testBeansType)

        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)

        assertThat((activity.findViewById(R.id.beans_name_edit_text) as EditText).text).isEqualTo(name)
    }

    @Test
    fun saveButton_savesDataInManager() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = createExampleBeansType()

        fillData(activity, testBeansType)
        ShadowView.clickOn(activity.findViewById(R.id.menu_item_save))

        argumentCaptor<BeansType>().apply {
            verify(beansTypeDataManager).saveBeansType(capture())
            assertThat(
                    firstValue.copy(
                            id = testBeansType.id,
                            photoFileName = testBeansType.photoFileName))
                    .isEqualToComparingFieldByField(testBeansType)
        }
    }

    @Test
    fun discardButton_discardsChanges() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = createExampleBeansType()

        fillData(activity, testBeansType)

        ShadowView.clickOn(activity.findViewById(R.id.menu_item_discard))

        verify(beansTypeDataManager, never()).saveBeansType(any())
    }

    @Test
    fun backButton_discardsChanges() {
        val activity = Robolectric.setupActivity(BeansTypeActivity::class.java)
        val testBeansType = createExampleBeansType()

        fillData(activity, testBeansType)

        activity.onBackPressed()

        verify(beansTypeDataManager, never()).saveBeansType(any())
    }

    private fun createExampleBeansType(): BeansType {
        val date = Calendar.getInstance().time
        return BeansType(name = "Test name", country = "Test country", roastLevel = 1, date = date)
    }

    private fun fillData(activity: Activity, beansType: BeansType) {
        (activity.findViewById(R.id.beans_name_edit_text) as EditText).setText(beansType.name)
        (activity.findViewById(R.id.beans_country_edit_text) as EditText)
                .setText(beansType.country)
        (activity.findViewById(R.id.roast_level_button) as Button).text =
                beansType.roastLevel.toString()

        val calendar = Calendar.getInstance()
        calendar.time = beansType.date
        ShadowView.clickOn(activity.findViewById(R.id.date_picker_button))
        val dialog = ShadowDatePickerDialog.getLatestDialog() as DatePickerDialog
        dialog.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick()
    }
}