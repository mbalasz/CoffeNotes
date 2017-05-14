package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class BeansTypeListActivity extends SingleFragmentActivity {
  private static final String EXTRA_BEANS_TYPE_ID = "beans_type_id";

  public static Intent newIntent(Context packageContext, UUID selectedBeansTypeId) {
    Intent intent = new Intent(packageContext, BeansTypeListActivity.class);
    intent.putExtra(EXTRA_BEANS_TYPE_ID, selectedBeansTypeId);
    return intent;
  }

  @Override
  protected Fragment createFragment() {
    UUID selectedBeansTypeId = (UUID) getIntent().getSerializableExtra(EXTRA_BEANS_TYPE_ID);
    return BeansTypeListFragment.newInstance(selectedBeansTypeId);
  }
}
