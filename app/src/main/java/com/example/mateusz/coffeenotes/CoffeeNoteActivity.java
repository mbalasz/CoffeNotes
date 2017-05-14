package com.example.mateusz.coffeenotes;

import android.support.v4.app.Fragment;

public class CoffeeNoteActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return CoffeeNoteFragment.newInstance();
  }
}
