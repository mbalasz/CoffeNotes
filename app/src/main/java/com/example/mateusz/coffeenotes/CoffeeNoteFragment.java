package com.example.mateusz.coffeenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class CoffeeNoteFragment extends Fragment {
  private static final int SELECT_COFFEE_BEANS_TYPE_REQUEST = 1;

  private CoffeeNote coffeeNote;
  private Spinner coffeeTypeSpinner;
  private CardView beansTypeCardView;
  private TextView beansNameTextView;
  private TextView beansCountryTextView;

  public CoffeeNoteFragment() {

  }

  @NonNull
  public static CoffeeNoteFragment newInstance() {
    return new CoffeeNoteFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // TODO implement restoring coffee note from saved instance state.
    coffeeNote = new CoffeeNote();
    // TODO remove this.
    coffeeNote.setBeansType(BeansTypeDataManager.getInstance().getBeansTypeList().get(2));
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_coffee_note, container, false);

    createCoffeeTypeSpinner(view);
    createBeansTypeCardView(view);

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SELECT_COFFEE_BEANS_TYPE_REQUEST) {
      if (resultCode == RESULT_OK) {

      }
    }
  }

  private void createCoffeeTypeSpinner(@NonNull View parentView) {
    coffeeTypeSpinner = (Spinner) parentView.findViewById(R.id.coffee_type_spinner);
    ArrayAdapter<CoffeeType> adapter =
        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CoffeeType.values());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    coffeeTypeSpinner.setAdapter(adapter);

    coffeeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(@NonNull AdapterView<?> parent, View view, int position, long id) {
        CoffeeType coffeeType = (CoffeeType) parent.getItemAtPosition(position);
        coffeeNote.setCoffeeType(coffeeType);
        Toast.makeText(getContext(), coffeeType.toString(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  private void createBeansTypeCardView(@NonNull View parentView) {
    beansTypeCardView = (CardView) parentView.findViewById(R.id.beans_type_card_view);
    beansTypeCardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent =
            BeansTypeListActivity.newIntent(getContext(), coffeeNote.getBeansType().getId());
        startActivityForResult(intent, SELECT_COFFEE_BEANS_TYPE_REQUEST);
      }
    });

    beansNameTextView = (TextView) parentView.findViewById(R.id.beans_name_text_view);
    beansNameTextView.setText(coffeeNote.getBeansType().getName());

    beansCountryTextView = (TextView) parentView.findViewById(R.id.beans_country_text_view);
    beansCountryTextView.setText(coffeeNote.getBeansType().getCountry());
  }
}
