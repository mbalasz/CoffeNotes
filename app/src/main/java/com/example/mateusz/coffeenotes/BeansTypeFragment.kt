package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.bindView

import java.util.UUID

class BeansTypeFragment : ListenableFragment() {
    private lateinit var beansType: BeansType
    private val beansNameEditText: EditText by bindView(R.id.beans_name_edit_text)
    private val beansCountryEditText: EditText by bindView(R.id.beans_country_edit_text)
    private val roastLevelButton: Button by bindView(R.id.roast_level_button)

    private lateinit var onBeansTypeEditFinishedListener: OnBeansTypeEditFinishedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            val beansTypeId = arguments.getSerializable(ARG_BEANS_TYPE_ID) as UUID
            beansType =
                    BeansTypeDataManager.instance(context)
                            .getBeansTypeById(beansTypeId) ?: BeansType()
        } else {
            beansType = BeansType()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_beans_type, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roastLevelButton.setOnClickListener {
            val roastPickerFragment = RoastPickerFragment.newInstance(beansType.roastLevel)
            roastPickerFragment.setTargetFragment(this@BeansTypeFragment, REQUEST_ROAST_PICKER)
            roastPickerFragment.show(fragmentManager, TAG_DIALOG_ROAST_PICKER)
        }
        updateUi()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeEditFinishedListener = context as OnBeansTypeEditFinishedListener
        } catch (e: ClassCastException) {
            onNonListenerContextAttached(context)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_beans_type_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_save_beans_type -> {
                onSaveBeansType()
                onBeansTypeEditFinishedListener.onBeansTypeSaved()
                return true
            }
            R.id.menu_item_discard_beans_type -> {
                onBeansTypeEditFinishedListener.onBeansTypeDiscarded()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_ROAST_PICKER -> {
                data?.let {
                    roastLevelButton.text =
                            data.getIntExtra(RoastPickerFragment.EXTRA_ROAST_LEVEL, 0).toString()
                }
            }
        }
    }

    private fun onSaveBeansType() {
        beansType.name = beansNameEditText.text.toString()
        beansType.country = beansCountryEditText.text.toString()
        beansType.roastLevel = roastLevelButton.text.toString().toInt()
        BeansTypeDataManager.instance(context).saveBeansType(beansType)
    }

    private fun updateUi() {
        beansNameEditText.setText(beansType.name)
        roastLevelButton.text = beansType.roastLevel.toString()
    }

    internal interface OnBeansTypeEditFinishedListener {
        fun onBeansTypeSaved()

        fun onBeansTypeDiscarded()
    }

    companion object {
        private val ARG_BEANS_TYPE_ID = "beans_type_id"
        private val TAG_DIALOG_ROAST_PICKER = "DialogRoastPicker"
        private val REQUEST_ROAST_PICKER = 0

        fun newInstance(beansTypeId: UUID): BeansTypeFragment {
            val args = Bundle()
            args.putSerializable(ARG_BEANS_TYPE_ID, beansTypeId)
            val fragment = newInstance()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): BeansTypeFragment {
            return BeansTypeFragment()
        }
    }
}
