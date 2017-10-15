package com.example.mateusz.coffeenotes

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import com.example.mateusz.coffeenotes.view.ContentViewHolder
import com.example.mateusz.coffeenotes.view.EditableListFragment
import javax.inject.Inject

class CoffeeNoteListFragment : EditableListFragment<CoffeeNote>() {

    @Inject lateinit var beansTypeDataManager: BeansTypeDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context.applicationContext as MyApplication).getAppComponent().inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_EDIT_COFFEE_NOTE -> {
                if (resultCode == RESULT_OK) {
                    notifyDataChanged()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val isConsumed = super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_item_list_new_item -> {
                val intent = CoffeeNoteActivity.newIntent(context)
                startActivityForResult(intent, REQUEST_EDIT_COFFEE_NOTE)
                return true
            }
            else -> return isConsumed
        }
    }

    override fun getDataList(): List<CoffeeNote> {
        return beansTypeDataManager.getCoffeeNotes()
    }

    override fun createContentViewHolder(): ContentViewHolder<CoffeeNote> {
        return object : ContentViewHolder<CoffeeNote>() {
            private val coffeeNoteNameTextView: TextView

            init {
                val inflater = LayoutInflater.from(context)
                view = inflater.inflate(R.layout.item_coffee_note_content_view, null, false)

                coffeeNoteNameTextView =
                        view.findViewById(R.id.item_coffee_note_row_name_text_view) as TextView
            }

            override fun onClicked() {
                startActivityForResult(
                        CoffeeNoteActivity.newIntent(context, data.uuid),
                        REQUEST_EDIT_COFFEE_NOTE)
            }

            override fun onUpdateView() {
                coffeeNoteNameTextView.text = data.title
            }

            override fun onRecycle() {}

        }
    }

    override fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<CoffeeNote>) {}

    companion object {
        val REQUEST_EDIT_COFFEE_NOTE = 0

        fun newInstance(): CoffeeNoteListFragment {
            return CoffeeNoteListFragment()
        }
    }
}