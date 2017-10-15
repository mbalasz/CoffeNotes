package com.example.mateusz.coffeenotes

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.view.ContentViewHolder
import com.example.mateusz.coffeenotes.view.EditableListFragment
import java.util.*
import javax.inject.Inject

class BeansTypeListFragment : EditableListFragment<BeansType>() {

    @Inject lateinit var beansTypeDataManager: BeansTypeDataManager

    private var highlightedBeansTypeId: UUID? = null

    private lateinit var onBeansTypeSelectedListener: OnBeansTypeSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as MyApplication).getAppComponent().inject(this)
        val args = arguments
        if (args != null) {
            if (args.containsKey(ARG_HIGHLIGHTED_BEANS_TYPE_ID)) {
                highlightedBeansTypeId = args.getSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID) as UUID
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeSelectedListener = context as OnBeansTypeSelectedListener
        } catch (e: ClassCastException) {
            throw RuntimeException(
                    String.format(
                            "%s must implement %s interface",
                            context?.javaClass?.simpleName ?: "[null]",
                            this.javaClass.simpleName))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val isConsumed = super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_item_list_new_item -> {
                val intent = BeansTypeActivity.newIntent(context)
                startActivityForResult(intent, EDIT_BEANS_TYPE_REQUEST)
                return true
            }
            else -> return isConsumed
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK) {
                notifyDataChanged()
            }
        }
    }

    interface OnBeansTypeSelectedListener {
        fun onBeansTypeSelected(beansType: BeansType)
    }

    override fun getDataList(): List<BeansType> {
        return beansTypeDataManager.getBeansTypes()
    }

    override fun createContentViewHolder(): ContentViewHolder<BeansType> {
        return object : ContentViewHolder<BeansType>() {
            private val beansNameTextView: TextView
            private val beansCountryTextView: TextView

            init {
                val inflater = LayoutInflater.from(context)
                view = inflater.inflate(R.layout.item_beans_type_content_view, null, false)

                beansNameTextView =
                        view.findViewById(R.id.item_beans_type_row_name_text_view) as TextView
                beansCountryTextView =
                        view.findViewById(R.id.item_beans_type_row_country_text_view) as TextView
            }

            override fun onClicked() {
                if (!isInEditMode) {
                    onBeansTypeSelectedListener.onBeansTypeSelected(data)
                } else {
                    val intent = BeansTypeActivity.newIntent(context, data.id)
                    startActivityForResult(intent, EDIT_BEANS_TYPE_REQUEST)
                }
            }

            override fun onUpdateView() {
                beansNameTextView.text = data.name
                beansCountryTextView.text = data.country
                if (data.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.BOLD)
                }
            }

            override fun onRecycle() {
                if (data.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    override fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<BeansType>) {
        // TODO: remove this tight coupling to the data manager. Create a recyclerView,
        // which operates on Cursor instead.
        beansTypeDataManager.removeBeansType(contentViewHolder.data)
        adapter.setDataList(beansTypeDataManager.getBeansTypes())
    }

    companion object {
        private val ARG_HIGHLIGHTED_BEANS_TYPE_ID = "highlighted_beans_type_id"
        private val EDIT_BEANS_TYPE_REQUEST = 1

        fun newInstance(highlightedBeansTypeId: UUID): BeansTypeListFragment {
            val args = Bundle()
            args.putSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID, highlightedBeansTypeId)
            val beansTypeListFragment = BeansTypeListFragment()
            beansTypeListFragment.arguments = args
            return beansTypeListFragment
        }

        fun newInstance(): BeansTypeListFragment {
            return BeansTypeListFragment()
        }
    }

}
