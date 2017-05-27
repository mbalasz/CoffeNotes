package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class BeansTypeFragment extends Fragment {
  private static final String ARG_BEANS_TYPE_ID = "beans_type_id";

  private BeansType beansType;
  private EditText beansNameEditText;

  private OnBeansTypeEditFinishedListener onBeansTypeEditFinishedListener;

  public BeansTypeFragment() {
  }

  @NonNull
  public static BeansTypeFragment newInstance(UUID beansTypeId) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_BEANS_TYPE_ID, beansTypeId);
    BeansTypeFragment fragment = new BeansTypeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    if (args != null) {
      UUID beansTypeId = (UUID) getArguments().getSerializable(ARG_BEANS_TYPE_ID);
      beansType = BeansTypeDataManager.getInstance().getBeansTypeById(beansTypeId);
    }
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_beans_type, container, false);

    createBeansNameEditText(view);

    updateUi();

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      onBeansTypeEditFinishedListener = (OnBeansTypeEditFinishedListener) context;
    } catch (ClassCastException e) {
      throw new RuntimeException(
          String.format(
              "%s has to implement %s interface",
              context.getClass().getSimpleName(),
              OnBeansTypeEditFinishedListener.class.getSimpleName()));

    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_beans_type_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_save_beans_type:
        saveBeansType();
        onBeansTypeEditFinishedListener.onBeansTypeSaved();
        return true;
      case R.id.menu_item_discard_beans_type:
        onBeansTypeEditFinishedListener.onBeansTypeDiscarded();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  interface OnBeansTypeEditFinishedListener {
    void onBeansTypeSaved();

    void onBeansTypeDiscarded();
  }

  private void saveBeansType() {
    if (beansType == null) {
      beansType = new BeansType();
      BeansTypeDataManager.getInstance().addBeansType(beansType);
    }
    beansType.setName(beansNameEditText.getText().toString());
  }

  private void createBeansNameEditText(@NonNull View parentView) {
    beansNameEditText = (EditText) parentView.findViewById(R.id.beans_name_edit_text);
  }

  private void updateUi() {
    if (beansType != null) {
      beansNameEditText.setText(beansType.getName());
    }
  }
}
