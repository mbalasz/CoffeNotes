package com.example.mateusz.coffeenotes

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import kotterknife.bindView
import java.util.*
import javax.inject.Inject

class CoffeeNoteFragment : Fragment() {
    private val SELECT_COFFEE_BEANS_TYPE_REQUEST = 1

    @Inject lateinit var beansTypeDataManager: BeansTypeDataManager

    private lateinit var onCoffeeNoteEditFinishedListener: OnCoffeeNoteEditFinishedListener

    private lateinit var coffeeNote: CoffeeNote
    private val coffeeNoteTitleEditText: EditText by bindView(R.id.coffee_note_title)
    private val coffeeTypeSpinner: Spinner by bindView(R.id.coffee_type_spinner)
    private val beansTypeCardView: CardView by bindView(R.id.beans_type_card_view)
    private val beansNameTextView: TextView by bindView(R.id.beans_name_text_view)
    private val beansCountryTextView: TextView by bindView(R.id.beans_country_text_view)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onCoffeeNoteEditFinishedListener = context as OnCoffeeNoteEditFinishedListener
        } catch (e: ClassCastException) {
            throw RuntimeException(
                    String.format(
                            "%s must implement %s interface",
                            context?.javaClass?.simpleName ?: "[null]",
                            this.javaClass.simpleName))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as MyApplication).getAppComponent().inject(this)

        if (arguments != null) {
            val coffeeNoteId = (arguments.getSerializable(ARGS_COFFEE_NOTE_ID) as UUID)
            coffeeNote = beansTypeDataManager.getCoffeeNoteById(coffeeNoteId) ?: CoffeeNote()
        } else {
            coffeeNote = CoffeeNote()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_coffee_note, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCoffeeNoteTitleEditText()
        initCoffeeTypeSpinner()
        initBeansTypeCardView()
        updateUi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_COFFEE_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                val selectedBeansTypeId =
                        data.getSerializableExtra(
                                BeansTypeListActivity.EXTRA_SELECTED_BEANS_TYPE_ID) as UUID
                coffeeNote.beansTypeId = selectedBeansTypeId
                updateBeansTypeCardViewUi()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.discard_or_save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_save -> {
                beansTypeDataManager.saveCoffeeNote(coffeeNote)
                onCoffeeNoteEditFinishedListener.onCoffeeNoteSaved()
                return true
            }
            R.id.menu_item_discard -> {
                onCoffeeNoteEditFinishedListener.onCoffeeNoteDiscarded()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    internal interface OnCoffeeNoteEditFinishedListener {
        fun onCoffeeNoteSaved()

        fun onCoffeeNoteDiscarded()
    }

    private fun updateUi() {
        coffeeNoteTitleEditText.setText(coffeeNote.title)
        updateBeansTypeCardViewUi()
    }

    private fun initCoffeeNoteTitleEditText() {
        coffeeNoteTitleEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                coffeeNote.title = s.toString()
            }

        })
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
                coffeeNote.coffeeType = coffeeType
                Toast.makeText(context, coffeeType.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun initBeansTypeCardView() {
        beansTypeCardView.setOnClickListener {
            val intent = BeansTypeListActivity.newIntent(context, coffeeNote.beansTypeId)
            startActivityForResult(intent, SELECT_COFFEE_BEANS_TYPE_REQUEST)
        }

        updateBeansTypeCardViewUi()
    }

    private fun updateBeansTypeCardViewUi() {
        coffeeNote.beansTypeId?.let {
            val selectedBeansType = beansTypeDataManager.getBeansTypeById(it)
            beansNameTextView.text = selectedBeansType?.name
            beansCountryTextView.text = selectedBeansType?.country
        }
    }

    companion object {
        private val ARGS_COFFEE_NOTE_ID = "coffee_note_id"

        fun newInstance(coffeeNoteId: UUID): CoffeeNoteFragment {
            val args = Bundle()
            args.putSerializable(ARGS_COFFEE_NOTE_ID, coffeeNoteId)
            val fragment = newInstance()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): CoffeeNoteFragment {
            return CoffeeNoteFragment()
        }
    }
}
