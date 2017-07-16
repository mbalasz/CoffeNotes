package com.example.mateusz.coffeenotes

import android.support.v4.app.Fragment

/**
 * Helper class which provides a fragment that is about to be tested to a
 * {@link FragmentTestActivity}.
 *
 * <p>This class is used by {@link FragmentTestRule}, which sets a tested Fragment, before
 * Activity is launched.
 *
 * <p>When testing Fragments, we attach them to a {@code FragmentTestActivity}. However, when
 * Fragments are attached after Activity is created, there's a racing condition between Fragment
 * transaction and test execution. The test starts its execution before the fragment gets attached.
 *
 * <p>FragmentTestRule sets a fragment in this class and the tested activity uses that
 * fragment in its onCreate method.
 */
class FragmentTestUtil {
    companion object {
        var fragment: Fragment? = null
    }
}