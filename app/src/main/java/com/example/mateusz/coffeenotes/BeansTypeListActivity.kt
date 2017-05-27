package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

import java.util.UUID

class BeansTypeListActivity : SingleFragmentActivity(), BeansTypeListFragment.OnBeansTypeSelectedListener {

    override fun createFragment(): Fragment {
        val intent = intent
        if (intent.hasExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID)) {
            val selectedBeansTypeId = getIntent().getSerializableExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID) as UUID
            return BeansTypeListFragment.newInstance(selectedBeansTypeId)
        }
        return BeansTypeListFragment.newInstance()
    }

    override fun onBeansTypeSelected(beansType: BeansType) {
        val data = Intent()
        data.putExtra(EXTRA_SELECTED_BEANS_TYPE_ID, beansType.id)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        val EXTRA_SELECTED_BEANS_TYPE_ID = "selected_beans_type_id"
        private val EXTRA_HIGHLIGHTED_BEANS_TYPE_ID = "highlighted_beans_type_id"

        fun newIntent(packageContext: Context, selectedBeansTypeId: UUID): Intent {
            val intent = Intent(packageContext, BeansTypeListActivity::class.java)
            intent.putExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID, selectedBeansTypeId)
            return intent
        }
    }
}
