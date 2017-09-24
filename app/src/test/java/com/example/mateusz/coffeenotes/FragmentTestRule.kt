package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

class FragmentTestRule<T: Fragment>(val fragmentClass: Class<T>) : TestRule {
    lateinit var activityController: ActivityController<FragmentTestActivity>

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                setUpActivity()

                base?.evaluate()
            }
        }
    }

    private fun setUpActivity() {
        FragmentTestActivity.fragment = fragmentClass.newInstance()
        activityController =
                Robolectric.buildActivity(FragmentTestActivity::class.java)
                        .create()
                        .start()
                        .resume()
                        .visible()
    }
}