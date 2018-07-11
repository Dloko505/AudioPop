package com.adulgr.audio.audiopop.fragments.result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adulgr.audio.audiopop.R;

public class ResultListFragment extends Fragment {

  RecyclerView mResultRecyclerView;
  RecyclerView.Adapter mAdaptor;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("RESULTS");
  }

  public ResultListFragment() {
    // Left blank intentionally
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.result_list_item, container, false);

    updateUI();

    return view;
  }

  private void updateUI() {

  }

  private class ResultHolder extends RecyclerView.ViewHolder {

    public ResultHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.result_list_item, parent, false));
    }
  }

}
