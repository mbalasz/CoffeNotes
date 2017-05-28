package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment
import android.content.Context

open class ListenableFragment : Fragment() {
    protected fun onNonListenerContextAttached(context: Context?) {
        throw RuntimeException(
                String.format(
                        "%s must implement %s interface",
                        context?.javaClass?.simpleName ?: "[null]",
                        this.javaClass.simpleName))
    }
}