package com.adulgr.audio.audiopop.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adulgr.audio.audiopop.R;

public class SetupFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("Setup");
  }

  public SetupFragment() {
    // Left blank intentionally
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
       Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.setup_fragment, container, false);


    return v;
  }

}
