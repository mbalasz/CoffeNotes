package com.example.mateusz.coffeenotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class BeansTypeFragment extends Fragment {
  private static final String ARG_BEANS_TYPE_ID = "beans_type_id";

  private BeansType beansType;
  private EditText beansNameEditText;

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
    UUID beansTypeId = (UUID) getArguments().getSerializable(ARG_BEANS_TYPE_ID);
    beansType = BeansTypeDataManager.getInstance().getBeansTypeById(beansTypeId);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_beans_type, container, false);

    createBeansNameEditText(view);

    return view;
  }

  private void createBeansNameEditText(@NonNull View parentView) {
    beansNameEditText = (EditText) parentView.findViewById(R.id.beans_name_edit_text);
    beansNameEditText.setText(beansType.getName());
    beansNameEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
        beansType.setName(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {}
    });
  }

}
