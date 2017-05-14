package com.example.mateusz.coffeenotes;

import android.graphics.Typeface;
import android.os.Bundle;
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
  private static final String ARG_BEANS_TYPE_ID = "beans_type_id";
  private RecyclerView beansTypesRecyclerView;
  private UUID selectedBeansTypeId;

  public BeansTypeListFragment() {
    // Required empty public constructor
  }

  public static BeansTypeListFragment newInstance(UUID selectedBeansTypeId) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_BEANS_TYPE_ID, selectedBeansTypeId);
    BeansTypeListFragment beansTypeListFragment = new BeansTypeListFragment();
    beansTypeListFragment.setArguments(args);
    return beansTypeListFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    selectedBeansTypeId = (UUID) args.getSerializable(ARG_BEANS_TYPE_ID);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_beans_type_list, container, false);

    beansTypesRecyclerView = (RecyclerView) view.findViewById(R.id.beans_types_recycler_view);
    beansTypesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    beansTypesRecyclerView.setAdapter(
        new BeansTypeAdapter(BeansTypeDataManager.getInstance().getBeansTypeList()));

    return view;
  }

  private class BeansTypeAdapter extends RecyclerView.Adapter<BeansTypeAdapter.BeansTypeViewHolder> {
    private List<BeansType> beansTypesList;

    public BeansTypeAdapter(List<BeansType> beansTypesList) {
      this.beansTypesList = beansTypesList;
    }

    public class BeansTypeViewHolder extends RecyclerView.ViewHolder {
      public TextView beansNameTextView;

      public BeansTypeViewHolder(View itemView) {
        super(itemView);
        beansNameTextView = (TextView) itemView;
      }
    }

    @Override
    public BeansTypeAdapter.BeansTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
      return new BeansTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeansTypeAdapter.BeansTypeViewHolder holder, int position) {
      BeansType beansType = beansTypesList.get(position);
      holder.beansNameTextView.setText(beansType.getName());
      if (beansType.getId().equals(selectedBeansTypeId)) {
        holder.beansNameTextView.setTypeface(null, Typeface.BOLD);
      }
    }

    @Override
    public int getItemCount() {
      return beansTypesList.size();
    }
  }

}
