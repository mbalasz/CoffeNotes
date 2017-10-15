package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView.clickOn

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class BeansTypeListActivityTest {

    lateinit var mockBeansTypeDataManager: BeansTypeDataManager
    val appComponent: MyAppComponent =
            (RuntimeEnvironment.application as MyApplication).getAppComponent()

    @Before
    fun setUp() {
        mockBeansTypeDataManager = appComponent.dataManager()
    }

    @Test
    fun clickNewItem_firesIntentToBeansTypeActivity() {
        val activity = createActivity()

        clickOn(activity.findViewById(R.id.menu_item_list_start_edit))
        clickOn(activity.findViewById(R.id.menu_item_list_new_item))

        val shadowActivity = shadowOf(activity)
        val expectedIntent = BeansTypeActivity.newIntent(activity)
        assertThat(shadowActivity.nextStartedActivity.filterEquals(expectedIntent))
                .isEqualTo(true)
    }

    @Test
    fun clickListItem_notInEditMode_setsSelectedBeansTypeAsResult() {
        val beansType = BeansType()
        whenever(mockBeansTypeDataManager.getBeansTypes()).thenReturn(listOf(beansType))
        val activity = createActivity()
        val recyclerView = activity.findViewById(R.id.recycler_view) as RecyclerView

        clickOn(recyclerView.findViewHolderForAdapterPosition(0).itemView)

        val shadowActivity = Shadows.shadowOf(activity)

        assertThat(shadowActivity.resultCode).isEqualTo(Activity.RESULT_OK)
        val expectedResultIntent = Intent().putExtra(
                BeansTypeListActivity.EXTRA_SELECTED_BEANS_TYPE_ID, beansType.id)
        val actualIntent = shadowActivity.resultIntent
        assertThat(actualIntent.filterEquals(expectedResultIntent)).isTrue()
        assertThat(
                actualIntent.getSerializableExtra(
                        BeansTypeListActivity.EXTRA_SELECTED_BEANS_TYPE_ID))
                .isEqualTo(beansType.id)
    }

    @Test
    fun clickListItem_inEditMode_startBeansTypeActivity() {
        val beansType = BeansType()
        whenever(mockBeansTypeDataManager.getBeansTypes()).thenReturn(listOf(beansType))
        val activity = createActivity()
        val recyclerView = activity.findViewById(R.id.recycler_view) as RecyclerView

        clickOn(activity.findViewById(R.id.menu_item_list_start_edit))
        clickOn(recyclerView.findViewHolderForAdapterPosition(0).itemView)

        val expectedIntent = BeansTypeActivity.newIntent(activity, beansType.id)
        assertThat(shadowOf(activity).nextStartedActivity.filterEquals(expectedIntent))
                .isEqualTo(true)
    }

    private fun createActivity(): BeansTypeListActivity {
        return Robolectric.buildActivity(BeansTypeListActivity::class.java)
                .create()
                .start()
                .resume()
                .visible()
                .get()
    }
}