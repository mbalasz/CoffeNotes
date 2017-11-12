package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.UUID

class CoffeeNoteActivity
    : SingleFragmentActivity(), CoffeeNoteFragment.OnCoffeeNoteEditFinishedListener {

    override fun createFragment(): Fragment {
        val coffeeNoteId = intent.getSerializableExtra(EXTRA_COFFEE_NOTE_ID) as UUID?
        if (coffeeNoteId != null) {
            return CoffeeNoteFragment.newInstance(coffeeNoteId)
        }
        return CoffeeNoteFragment.newInstance()
    }

    override fun onCoffeeNoteSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onCoffeeNoteDiscarded() {
        setResult(Activity.RESULT_CANCELED)
        finish()
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
