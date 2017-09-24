package com.example.mateusz.coffeenotes

import android.support.v7.widget.RecyclerView
import com.example.mateusz.coffeenotes.application.MyAppComponent
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.*
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView.clickOn

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = TestApplication::class)
class BeansTypeListActivityTest {

    @Mock lateinit var mockBeansTypeDataManager: BeansTypeDataManager
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
    fun clickListItem_inEditMode_startBeansTypeActivity() {
        val beansType = BeansType()
        Mockito.`when`(mockBeansTypeDataManager.getBeansTypes()).thenReturn(listOf(beansType))
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