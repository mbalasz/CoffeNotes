package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class BeansTypeListActivity
    extends SingleFragmentActivity implements BeansTypeListFragment.OnBeansTypeSelectedListener {
  public static final String EXTRA_SELECTED_BEANS_TYPE_ID = "selected_beans_type_id";
  private static final String EXTRA_HIGHLIGHTED_BEANS_TYPE_ID = "highlighted_beans_type_id";

  @NonNull
  public static Intent newIntent(Context packageContext, UUID selectedBeansTypeId) {
    Intent intent = new Intent(packageContext, BeansTypeListActivity.class);
    intent.putExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID, selectedBeansTypeId);
    return intent;
  }

  @NonNull
  @Override
  protected Fragment createFragment() {
    Intent intent = getIntent();
    if (intent.hasExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID)) {
      UUID selectedBeansTypeId =
          (UUID) getIntent().getSerializableExtra(EXTRA_HIGHLIGHTED_BEANS_TYPE_ID);
      return BeansTypeListFragment.newInstance(selectedBeansTypeId);
    }
    return BeansTypeListFragment.newInstance();
  }

  @Override
  public void onBeansTypeSelected(BeansType beansType) {
    Intent data = new Intent();
    data.putExtra(EXTRA_SELECTED_BEANS_TYPE_ID, beansType.getId());
    setResult(RESULT_OK, data);
    finish();
  }
}
