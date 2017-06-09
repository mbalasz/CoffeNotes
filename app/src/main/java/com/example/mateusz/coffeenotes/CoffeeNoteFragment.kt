package com.example.mateusz.coffeenotes

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import java.util.UUID

import android.app.Activity.RESULT_OK
import butterknife.bindView

class CoffeeNoteFragment : Fragment() {
    private val SELECT_COFFEE_BEANS_TYPE_REQUEST = 1

    private var coffeeNote: CoffeeNote? = null
    private val coffeeTypeSpinner: Spinner by bindView(R.id.coffee_type_spinner)
    private val beansTypeCardView: CardView by bindView(R.id.beans_type_card_view)
    private val beansNameTextView: TextView by bindView(R.id.beans_name_text_view)
    private val beansCountryTextView: TextView by bindView(R.id.beans_country_text_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO implement restoring coffee note from saved instance state.
        coffeeNote = CoffeeNote()
        // TODO remove this.
//        coffeeNote?.beansType = BeansTypeDataManager.instance(context).getBeansTypeList()[2]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_coffee_note, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCoffeeTypeSpinner()
        initBeansTypeCardView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_COFFEE_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                val selectedBeansTypeId =
                        data.getSerializableExtra(
                                BeansTypeListActivity.EXTRA_SELECTED_BEANS_TYPE_ID) as UUID
                val selectedBeansType =
                        BeansTypeDataManager.instance(context).getBeansTypeById(selectedBeansTypeId)
                coffeeNote?.beansType = selectedBeansType
                updateBeansTypeCardViewUi()
            }
        }
    }

    private fun initCoffeeTypeSpinner() {
        val adapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, CoffeeType.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        coffeeTypeSpinner.adapter = adapter

        coffeeTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val coffeeType = parent.getItemAtPosition(position) as CoffeeType
                coffeeNote?.coffeeType = coffeeType
                Toast.makeText(context, coffeeType.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun initBeansTypeCardView() {
        beansTypeCardView.setOnClickListener {
            val intent = BeansTypeListActivity.newIntent(context, coffeeNote?.beansType?.id)
            startActivityForResult(intent, SELECT_COFFEE_BEANS_TYPE_REQUEST)
        }

        updateBeansTypeCardViewUi()
    }

    private fun updateBeansTypeCardViewUi() {
        coffeeNote?.beansType?.let {
            beansNameTextView.text = it.name
            beansCountryTextView.text = it.country
        }
    }

    companion object {

        fun newInstance(): CoffeeNoteFragment {
            return CoffeeNoteFragment()
        }
    }
}
