package com.example.mateusz.coffeenotes

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
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
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class BeansTypeActivityTest {

    private lateinit var beansTypeDataManager: BeansTypeDataManager
    private val appComponent =
            (RuntimeEnvironment.application as MyApplication).getAppComponent() as TestComponent

    @Before
    fun setUp() {
        beansTypeDataManager = appComponent.dataManager()
    }

    @Test
    fun initState_prefillFields() {
        val uuid = UUID.randomUUID()
        val name = "Test name"
        val country = "Test country"
        val roastLevel = 4
        val date = LocalDate.now()
        val testBeansType = BeansType(uuid, name, country, roastLevel, date = date)
        whenever(beansTypeDataManager.getBeansTypeById(uuid)).thenReturn(testBeansType)

        val activity =
                Robolectric.buildActivity(
                        BeansTypeActivity::class.java,
                        BeansTypeActivity.newIntent(RuntimeEnvironment.application, uuid))
                        .create()
                        .start()
                        .resume()
                        .visible()
                        .get()

        assertThat((activity.findViewById(R.id.beans_name_edit_text) as EditText).text.toString())
                .isEqualTo(name)
        assertThat((activity.findViewById(R.id.beans_country_edit_text) as EditText).text.toString())
                .isEqualTo(country)
        assertThat((activity.findViewById(R.id.roast_level_button) as Button).text.toString())
                .isEqualTo(roastLevel.toString())
        assertThat((activity.findViewById(R.id.date_picker_button) as Button).text.toString())
                .isEqualTo(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
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
        return BeansType(
                name = "Test name",
                country = "Test country",
                roastLevel = 1,
                date = LocalDate.now())
    }

    private fun fillData(activity: Activity, beansType: BeansType) {
        (activity.findViewById(R.id.beans_name_edit_text) as EditText).setText(beansType.name)
        (activity.findViewById(R.id.beans_country_edit_text) as EditText)
                .setText(beansType.country)
        (activity.findViewById(R.id.roast_level_button) as Button).text =
                beansType.roastLevel.toString()

        ShadowView.clickOn(activity.findViewById(R.id.date_picker_button))
        val dialog = ShadowDatePickerDialog.getLatestDialog() as DatePickerDialog
        dialog.updateDate(
                beansType.date.year,
                beansType.date.monthValue,
                beansType.date.dayOfMonth)
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick()
    }
}