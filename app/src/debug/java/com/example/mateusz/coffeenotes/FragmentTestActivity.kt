package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

class FragmentTestActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val fragment = fragment ?: throw RuntimeException(
                "FragmentTestActivity requires FragmentTestUtil to have a non-null fragment.")
        return fragment
    }

    companion object {
        /**
         * A Fragment that is about to be tested inside {@link FragmentTestActivity}
         *
         * <p>This Fragment is set by {@link FragmentTestRule} before the Activity is launched.
         *
         * <p>When testing Fragments, we attach them to a {@code FragmentTestActivity}. However,
         * when Fragments are attached after Activity is launched, there's a racing condition
         * between the Fragment transaction and test execution. The test starts its execution before
         * the fragment gets attached.
         */
        var fragment: Fragment? = null
    }
}
