package com.example.mateusz.coffeenotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CoffeeNoteFragment extends Fragment {
  private CoffeeNote coffeeNote;
  private Spinner coffeeTypeSpinner;

  public CoffeeNoteFragment() {
  }

  public static CoffeeNoteFragment newInstance() {
    return new CoffeeNoteFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_coffee_note, container, false);

    createCoffeeTypeSpinner(view);

    return view;
  }

  private void createCoffeeTypeSpinner(View parentView) {
    coffeeTypeSpinner = (Spinner) parentView.findViewById(R.id.coffee_type_spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(
            getContext(), R.array.coffee_types_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    coffeeTypeSpinner.setAdapter(adapter);

    coffeeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence coffeeType = (CharSequence) parent.getItemAtPosition(position);

        Toast.makeText(getContext(), coffeeType, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }
}
