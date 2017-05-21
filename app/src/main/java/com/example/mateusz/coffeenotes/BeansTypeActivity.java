package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class BeansTypeActivity extends SingleFragmentActivity {
  private static final String EXTRA_BEANS_TYPE_ID = "beans_type_id";

  public static Intent newIntent(Context packageContext, UUID beansTypeId) {
    Intent intent = new Intent(packageContext, BeansTypeActivity.class);
    intent.putExtra(EXTRA_BEANS_TYPE_ID, beansTypeId);
    return intent;
  }

  @NonNull
  @Override
  protected Fragment createFragment() {
    Intent intent = getIntent();
    UUID beansTypeId = (UUID) intent.getSerializableExtra(EXTRA_BEANS_TYPE_ID);
    return BeansTypeFragment.newInstance(beansTypeId);
  }
}
