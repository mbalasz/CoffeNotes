package com.example.mateusz.coffeenotes

import android.content.Intent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.assertj.core.api.Assertions.assertThat
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class BeansTypeListActivityTest {

    @Test
    fun clickNewItem_firesIntentToBeansTypeActivity() {
        val activity =
                Robolectric.buildActivity(BeansTypeListActivity::class.java)
                        .create()
                        .start()
                        .resume()
                        .visible()
                        .get()

        ShadowView.clickOn(activity.findViewById(R.id.menu_item_list_start_edit))
        ShadowView.clickOn(activity.findViewById(R.id.menu_item_list_new_item))

        val shadowActivity = Shadows.shadowOf(activity)
        val expectedIntent = BeansTypeActivity.newIntent(activity)
        assertThat(shadowActivity.nextStartedActivity.filterEquals(expectedIntent))
                .isEqualTo(true)
    }

}