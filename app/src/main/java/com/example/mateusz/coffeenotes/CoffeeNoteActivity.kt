package com.example.mateusz.coffeenotes

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

class CoffeeNoteActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        val coffeeNoteId = intent.getSerializableExtra(EXTRA_COFFEE_NOTE_ID) as UUID
        return CoffeeNoteFragment.newInstance(coffeeNoteId)
    }

    companion object {
        private val EXTRA_COFFEE_NOTE_ID = "coffee_note_id"

        fun newIntent(packageContext: Context, coffeeNoteId: UUID): Intent {
            val intent = Intent(packageContext, CoffeeNoteActivity::class.java)
            intent.putExtra(EXTRA_COFFEE_NOTE_ID, coffeeNoteId)
            return intent
        }
    }
}
