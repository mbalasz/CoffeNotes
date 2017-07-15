package com.example.mateusz.coffeenotes

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import butterknife.bindView
import com.example.mateusz.coffeenotes.view.RemovableViewHolder

// TODO: Create an abstract ListFragment, which common logic for RecyclerView and options menu.
class CoffeeNoteListFragment : ListenableFragment() {

    private val coffeeNoteRecyclerView: RecyclerView by bindView(R.id.coffee_note_recycler_view)
    private val coffeeNoteDataManager: CoffeeNoteDataManager by lazy {
        CoffeeNoteDataManager.instance(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_coffee_note_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coffeeNoteRecyclerView.adapter = CoffeeNoteAdapter(coffeeNoteDataManager.getCoffeeNotes())
        val layoutManager = LinearLayoutManager(context)
        coffeeNoteRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        coffeeNoteRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_EDIT_COFFEE_NOTE -> {
                if (resultCode == RESULT_OK) {
                    coffeeNoteRecyclerView.adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_list_new_item -> {
                val intent = CoffeeNoteActivity.newIntent(context)
                startActivityForResult(intent, REQUEST_EDIT_COFFEE_NOTE)
                setEditMode(false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



    private inner class CoffeeNoteAdapter(private var coffeeNoteList: List<CoffeeNote>)
        : RecyclerView.Adapter<CoffeeNoteAdapter.CoffeeNoteViewHolder>() {

        override fun onBindViewHolder(holder: CoffeeNoteViewHolder?, position: Int) {
            holder!!.bindCoffeeNote(coffeeNoteList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CoffeeNoteViewHolder {
            val view = LayoutInflater.from(context).inflate(
                    R.layout.item_coffee_note_row, parent, false)
            return CoffeeNoteViewHolder(view)
        }

        override fun getItemCount(): Int {
            return coffeeNoteList.size
        }

        private inner class CoffeeNoteViewHolder(itemView: View) : RemovableViewHolder(itemView) {
            private val coffeeNoteNameTextView: TextView =
                    itemView.findViewById(R.id.item_coffee_note_row_name_text_view) as TextView
            private lateinit var coffeeNote: CoffeeNote

            init {
                itemView.setOnClickListener {
                    startActivityForResult(
                            CoffeeNoteActivity.newIntent(context, coffeeNote.uuid),
                            REQUEST_EDIT_COFFEE_NOTE)
                }
            }

            override fun onRemoveItem() {
            }

            fun bindCoffeeNote(coffeeNote: CoffeeNote) {
                this.coffeeNote = coffeeNote
                updateViewHolder()
            }

            fun updateViewHolder() {
                coffeeNoteNameTextView.text = coffeeNote.title
            }
        }
    }

    companion object {
        val REQUEST_EDIT_COFFEE_NOTE = 0

        fun newInstance(): CoffeeNoteListFragment {
            return CoffeeNoteListFragment()
        }
    }
}