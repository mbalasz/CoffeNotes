package com.example.mateusz.coffeenotes.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.example.mateusz.coffeenotes.BuildConfig
import com.example.mateusz.coffeenotes.FragmentTestActivity
import com.example.mateusz.coffeenotes.FragmentTestRule
import com.example.mateusz.coffeenotes.R
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

        assertViewDoesNotExist(R.id.menu_item_list_start_edit)
        assertEditModeIsDisplayed(true)
    }

    @Test
    fun clickFinishEditItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_finish_edit)

        assertViewIsDisplayed(R.id.menu_item_list_start_edit)
        assertEditModeIsDisplayed(false)
    }

    @Test
    fun clickAddNewItem_TurnsOffEditMode() {
        clickView(R.id.menu_item_list_start_edit)

        clickView(R.id.menu_item_list_new_item)

        assertViewIsDisplayed(R.id.menu_item_list_start_edit)
        assertEditModeIsDisplayed(false)
    }

    @Test
    fun listIsPopulatedOnStartup() {
        val fragment = activityRule.getFragment() as EditableListFragmentForTest
        assertRecyclerViewContainsExactly(fragment.dataListForTest)
    }

    @Test
    fun removeItem() {
        val fragment = activityRule.getFragment() as EditableListFragmentForTest
        val recyclerView = activity.findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = recyclerView.layoutManager
        val initList = listOf(1, 2, 3, 4)
        fragment.setDataList(initList)
        assertThat(recyclerView.adapter.itemCount).isEqualTo(initList.size)

        clickView(R.id.menu_item_list_start_edit)
        val recyclerViewItem = layoutManager.findViewByPosition(0)
        ShadowView.clickOn(recyclerViewItem.findViewById(R.id.item_row_remove_button))

        assertThat(recyclerView.adapter.itemCount).isEqualTo(initList.size - 1)
        assertRecyclerViewContainsExactly(listOf(2, 3, 4))
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

    private fun assertRecyclerViewContainsExactly(testList: List<Int>) {
        val layoutManager =
                (activity.findViewById(R.id.recycler_view) as RecyclerView).layoutManager

        for (idx in 0..testList.size - 1) {
            val itemButton =
                    layoutManager.findViewByPosition(idx)
                            .findViewWithTag(EditableListFragmentForTest.BUTTON_TAG) as Button
            assertThat(itemButton.text).isEqualTo(testList[idx].toString())
        }
    }

    class EditableListFragmentForTest : EditableListFragment<Int>() {
        companion object {
            val BUTTON_TAG = "testButton"
        }

        var dataListForTest = mutableListOf<Int>()

        fun setDataList(dataList: List<Int>) {
            dataListForTest = dataList.toMutableList()
            adapter.setDataList(dataListForTest)
            adapter.notifyDataSetChanged()
        }

        override fun getDataList(): List<Int> {
            return dataListForTest
        }

        override fun createContentViewHolder(): ContentViewHolder<Int> {
            return object : ContentViewHolder<Int>() {
                private val button: Button = Button(context)
                init {
                    button.tag = BUTTON_TAG
                    view = button
                }

                override fun onClicked() {}

                override fun onUpdateView() {
                    button.text = data.toString()
                }

                override fun onRecycle() {}
            }
        }

        override fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<Int>) {
            // TODO: remove this tight coupling to the data manager. Create a recyclerView,
            // which operates on Cursor instead.
            dataListForTest.remove(contentViewHolder.data)
            adapter.setDataList(dataListForTest)
        }
    }
}