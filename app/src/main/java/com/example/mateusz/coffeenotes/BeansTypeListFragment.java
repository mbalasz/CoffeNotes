package com.example.mateusz.coffeenotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class BeansTypeListFragment extends Fragment {
  private static final String ARG_HIGHLIGHTED_BEANS_TYPE_ID = "highlighted_beans_type_id";
  private RecyclerView beansTypesRecyclerView;
  @Nullable private UUID highlightedBeansTypeId;
  private OnBeansTypeSelectedListener onBeansTypeSelectedListener;

  public BeansTypeListFragment() {
    // Required empty public constructor
  }

  @NonNull
  public static BeansTypeListFragment newInstance(UUID highlightedBeansTypeId) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID, highlightedBeansTypeId);
    BeansTypeListFragment beansTypeListFragment = new BeansTypeListFragment();
    beansTypeListFragment.setArguments(args);
    return beansTypeListFragment;
  }

  @NonNull
  public static BeansTypeListFragment newInstance() {
    return new BeansTypeListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    if (args.containsKey(ARG_HIGHLIGHTED_BEANS_TYPE_ID)) {
      highlightedBeansTypeId = (UUID) args.getSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_beans_type_list, container, false);

    beansTypesRecyclerView = (RecyclerView) view.findViewById(R.id.beans_types_recycler_view);
    beansTypesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    beansTypesRecyclerView.setAdapter(
        new BeansTypeAdapter(BeansTypeDataManager.getInstance().getBeansTypeList()));

    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      onBeansTypeSelectedListener = (OnBeansTypeSelectedListener) context;
    } catch (ClassCastException e) {
      throw new RuntimeException(
          String.format(
              "%s must implement %s interface",
              context.toString(),
              OnBeansTypeSelectedListener.class.toString()));
    }
  }

  public interface OnBeansTypeSelectedListener {
    void onBeansTypeSelected(BeansType beansType);
  }

  private class BeansTypeAdapter
      extends RecyclerView.Adapter<BeansTypeAdapter.BeansTypeViewHolder> {
    private List<BeansType> beansTypesList;

    public BeansTypeAdapter(List<BeansType> beansTypesList) {
      this.beansTypesList = beansTypesList;
    }

    public class BeansTypeViewHolder extends RecyclerView.ViewHolder {
      private TextView beansNameTextView;
      private BeansType beansType;

      public BeansTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        beansNameTextView = (TextView) itemView;
        beansNameTextView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            onBeansTypeSelectedListener.onBeansTypeSelected(beansType);
          }
        });
      }

      public void bindBeansType(BeansType beansType) {
        this.beansType = beansType;
        beansNameTextView.setText(beansType.getName());
        if (beansType.getId().equals(highlightedBeansTypeId)) {
          beansNameTextView.setTypeface(null, Typeface.BOLD);
        }
      }
    }

    @NonNull
    @Override
    public BeansTypeAdapter.BeansTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
      return new BeansTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
        @NonNull BeansTypeAdapter.BeansTypeViewHolder holder, int position) {
      holder.bindBeansType(beansTypesList.get(position));
    }

    @Override
    public int getItemCount() {
      return beansTypesList.size();
    }
  }

}
