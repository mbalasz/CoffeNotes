package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class BeansTypeListActivity extends SingleFragmentActivity {
  private static final String EXTRA_SELECTED_BEANS_TYPE_ID = "beans_type_id";

  @NonNull
  public static Intent newIntent(Context packageContext, UUID selectedBeansTypeId) {
    Intent intent = new Intent(packageContext, BeansTypeListActivity.class);
    intent.putExtra(EXTRA_SELECTED_BEANS_TYPE_ID, selectedBeansTypeId);
    return intent;
  }

  @NonNull
  @Override
  protected Fragment createFragment() {
    Intent intent = getIntent();
    if (intent.hasExtra(EXTRA_SELECTED_BEANS_TYPE_ID)) {
      UUID selectedBeansTypeId =
          (UUID) getIntent().getSerializableExtra(EXTRA_SELECTED_BEANS_TYPE_ID);
      return BeansTypeListFragment.newInstance(selectedBeansTypeId);
    }
    return BeansTypeListFragment.newInstance();
  }
}
