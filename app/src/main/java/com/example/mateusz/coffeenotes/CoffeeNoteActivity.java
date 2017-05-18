package com.example.mateusz.coffeenotes;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class CoffeeNoteActivity extends SingleFragmentActivity {

  @NonNull
  @Override
  protected Fragment createFragment() {
    return CoffeeNoteFragment.newInstance();
  }
}
