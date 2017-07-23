package com.example.mateusz.coffeenotes

import android.view.View
import android.widget.Button
import com.example.mateusz.coffeenotes.view.ContentViewHolder
import com.example.mateusz.coffeenotes.view.EditableListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowView

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class EditableListFragmentTest {

    @Rule @JvmField
    val activityRule = FragmentTestRule(EditableListFragmentForTest::class.java)
    val activity: FragmentTestActivity by lazy {
        activityRule.activityController.get()
    }

    @Test
    fun editModeItemsHiddenByDefault() {
        assertViewIsDisplayed(R.id.menu_item_list_start_edit)

        assertEditModeIsDisplayed(false)
    }

    @Test
    fun clickEditMenuItem_TurnsOnEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        assertEditModeIsDisplayed(true)
    }

    @Test
    fun clickFinishEditItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_finish_edit)
        assertEditModeIsDisplayed(false)
    }

    @Test
    fun clickAddNewItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)
        assertEditModeIsDisplayed(false)
    }

    private fun clickView(viewId: Int) {
        ShadowView.clickOn(activity.findViewById(viewId))
    }

    private fun assertViewIsDisplayed(viewId: Int) {
        val view = activity.findViewById(viewId)
        assertThat(view).isNotNull()
        assertThat(view.visibility).isEqualTo(View.VISIBLE)
    }

    private fun assertViewDoesNotExist(viewId: Int) {
        assertThat(activity.findViewById(viewId)).isNull()
    }

    private fun assertEditModeIsDisplayed(isDisplayed: Boolean) {
        if (isDisplayed) {
            assertViewIsDisplayed(R.id.menu_item_list_finish_edit)
            assertViewIsDisplayed(R.id.menu_item_list_new_item)
        } else {
            assertViewDoesNotExist(R.id.menu_item_list_finish_edit)
            assertViewDoesNotExist(R.id.menu_item_list_new_item)
        }
    }

    class EditableListFragmentForTest : EditableListFragment<Int>() {

        override fun getDataList(): List<Int> {
            return listOf(1, 2, 3, 4)
        }

        override fun createContentViewHolder(): ContentViewHolder<Int> {
            return object : ContentViewHolder<Int>() {
                private val button: Button = Button(context)
                init {
                    view = button
                }

                override fun onClicked() {}

                override fun onUpdateView() {
                    button.text = data.toString()
                }

                override fun onRecycle() {}
            }
        }

        override fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<Int>) {}
    }
}