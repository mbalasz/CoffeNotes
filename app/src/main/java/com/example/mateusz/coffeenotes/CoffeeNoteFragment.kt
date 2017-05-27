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

class CoffeeNoteFragment : Fragment() {

    private lateinit var coffeeNote: CoffeeNote
    private lateinit var coffeeTypeSpinner: Spinner
    private lateinit var beansTypeCardView: CardView
    private lateinit var beansNameTextView: TextView
    private lateinit var beansCountryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO implement restoring coffee note from saved instance state.
        coffeeNote = CoffeeNote()
        // TODO remove this.
        coffeeNote.beansType = BeansTypeDataManager.instance.beansTypeList[2]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coffee_note, container, false)

        createCoffeeTypeSpinner(view)
        createBeansTypeCardView(view)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_COFFEE_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                val selectedBeansTypeId = data.getSerializableExtra(BeansTypeListActivity.EXTRA_SELECTED_BEANS_TYPE_ID) as UUID
                val selectedBeansType = BeansTypeDataManager.instance.getBeansTypeById(selectedBeansTypeId)
                coffeeNote.beansType = selectedBeansType
                updateBeansTypeCardViewUi()
            }
        }
    }

    private fun createCoffeeTypeSpinner(parentView: View) {
        coffeeTypeSpinner = parentView.findViewById(R.id.coffee_type_spinner) as Spinner
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, CoffeeType.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        coffeeTypeSpinner.adapter = adapter

        coffeeTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val coffeeType = parent.getItemAtPosition(position) as CoffeeType
                coffeeNote.setCoffeeType(coffeeType)
                Toast.makeText(context, coffeeType.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun createBeansTypeCardView(parentView: View) {
        beansTypeCardView = parentView.findViewById(R.id.beans_type_card_view) as CardView
        beansTypeCardView.setOnClickListener {
            val intent = BeansTypeListActivity.newIntent(context, coffeeNote.beansType!!.id!!)
            startActivityForResult(intent, SELECT_COFFEE_BEANS_TYPE_REQUEST)
        }

        beansNameTextView = parentView.findViewById(R.id.beans_name_text_view) as TextView
        beansCountryTextView = parentView.findViewById(R.id.beans_country_text_view) as TextView
        updateBeansTypeCardViewUi()
    }

    private fun updateBeansTypeCardViewUi() {
        beansNameTextView.text = coffeeNote.beansType!!.name
        beansCountryTextView.text = coffeeNote.beansType!!.country
    }

    companion object {
        private val SELECT_COFFEE_BEANS_TYPE_REQUEST = 1

        fun newInstance(): CoffeeNoteFragment {
            return CoffeeNoteFragment()
        }
    }
}
