package com.adulgr.audio.audiopop.fragments.result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adulgr.audio.audiopop.R;

public class ResultFragment extends Fragment {

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

  public ResultFragment() {
    // Left blank intentionally
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.result_fragment, container, false);
  }

}
