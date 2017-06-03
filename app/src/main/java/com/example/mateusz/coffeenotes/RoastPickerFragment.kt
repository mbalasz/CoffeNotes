package com.example.mateusz.coffeenotes

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker

class RoastPickerFragment : DialogFragment() {
    private lateinit var numberPicker: NumberPicker

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_roast_picker, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_roast_picker, null)

        numberPicker = view.findViewById(R.id.roast_number_picker) as NumberPicker
        numberPicker.minValue = MIN_ROAST_VALUE
        numberPicker.maxValue = MAX_ROAST_VALUE
        numberPicker.value = arguments.getInt(ARG_ROAST_LEVEL)
        numberPicker.displayedValues = Array(MAX_ROAST_VALUE, { i -> (i + 1).toString()})

        return AlertDialog.Builder(context)
                .setView(view)
                .setTitle(R.string.roast_picker_label)
                .setPositiveButton(android.R.string.ok, {_, _ -> onRoastLevelSelected()})
                .create()
    }

    private fun onRoastLevelSelected() {
        targetFragment?.let {
            val intent = Intent()
            intent.putExtra(EXTRA_ROAST_LEVEL, numberPicker.value)

            it.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        }
    }

    companion object {
        private val MIN_ROAST_VALUE = 1
        private val MAX_ROAST_VALUE = 10
        val EXTRA_ROAST_LEVEL = "extra_roast_level"
        val ARG_ROAST_LEVEL = "arg_roast_level"

        fun newInstance(roastLevel: Int): RoastPickerFragment{
            val args = Bundle()
            args.putInt(ARG_ROAST_LEVEL, roastLevel)
            val fragment = RoastPickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
