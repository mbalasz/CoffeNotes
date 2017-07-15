package com.example.mateusz.coffeenotes

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

class CoffeeNoteActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        val coffeeNoteId = intent.getSerializableExtra(EXTRA_COFFEE_NOTE_ID) as UUID?
        if (coffeeNoteId != null) {
            return CoffeeNoteFragment.newInstance(coffeeNoteId)
        }
        return CoffeeNoteFragment.newInstance()
    }

    companion object {
        private val EXTRA_COFFEE_NOTE_ID = "coffee_note_id"

        fun newIntent(packageContext: Context, coffeeNoteId: UUID): Intent {
            val intent = newIntent(packageContext)
            intent.putExtra(EXTRA_COFFEE_NOTE_ID, coffeeNoteId)
            return intent
        }

        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, CoffeeNoteActivity::class.java)
        }
    }
}
