package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        val fragmentManager = supportFragmentManager
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            val fragment = createFragment()
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    protected abstract fun createFragment(): Fragment
}
