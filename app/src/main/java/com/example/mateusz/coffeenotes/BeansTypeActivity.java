package com.example.mateusz.coffeenotes;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeansTypeActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return BeansTypeFragment.newInstance();
  }
}
