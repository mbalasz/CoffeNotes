package com.example.mateusz.coffeenotes

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.bindView

import java.util.UUID

class BeansTypeFragment : Fragment() {

    private var beansType: BeansType? = null
    private val beansNameEditText: EditText by bindView(R.id.beans_name_edit_text)

    private lateinit var onBeansTypeEditFinishedListener: OnBeansTypeEditFinishedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            val beansTypeId = arguments.getSerializable(ARG_BEANS_TYPE_ID) as UUID
            beansType = BeansTypeDataManager.instance.getBeansTypeById(beansTypeId)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beans_type, container, false)

        updateUi()

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeEditFinishedListener = context as OnBeansTypeEditFinishedListener
        } catch (e: ClassCastException) {
            throw RuntimeException(
                    String.format(
                            "%s has to implement %s interface",
                            context!!.javaClass.simpleName,
                            OnBeansTypeEditFinishedListener::class.java.simpleName))

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_beans_type_menu, menu)
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

    internal interface OnBeansTypeEditFinishedListener {
        fun onBeansTypeSaved()

        fun onBeansTypeDiscarded()
    }

    private fun onSaveBeansType() {
        if (beansType == null) {
            beansType = BeansTypeDataManager.instance.createBeansType()
        }
        beansType!!.name = beansNameEditText.text.toString()
    }

    private fun updateUi() {
        if (beansType != null) {
            beansNameEditText.setText(beansType!!.name)
        }
    }

    companion object {
        private val ARG_BEANS_TYPE_ID = "beans_type_id"

        fun newInstance(beansTypeId: UUID): BeansTypeFragment {
            val args = Bundle()
            args.putSerializable(ARG_BEANS_TYPE_ID, beansTypeId)
            val fragment = newInstance()
            fragment.arguments = args
            return fragment
        }

        fun newInstance() : BeansTypeFragment {
            return BeansTypeFragment()
        }
    }
}
