package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

import java.util.UUID

class BeansTypeActivity : SingleFragmentActivity(), BeansTypeFragment.OnBeansTypeEditFinishedListener {

    override fun createFragment(): Fragment {
        val intent = intent
        val beansTypeId = intent.getSerializableExtra(EXTRA_BEANS_TYPE_ID) as? UUID
                ?: return BeansTypeFragment.newInstance()
        return BeansTypeFragment.newInstance(beansTypeId)
    }

    override fun onBeansTypeSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onBeansTypeDiscarded() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        private val EXTRA_BEANS_TYPE_ID = "beans_type_id"

        fun newIntent(packageContext: Context, beansTypeId: UUID): Intent {
            val intent = newIntent(packageContext)
            intent.putExtra(EXTRA_BEANS_TYPE_ID, beansTypeId)
            return intent
        }

        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, BeansTypeActivity::class.java)
        }
    }
}
