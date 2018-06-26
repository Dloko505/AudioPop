package com.adulgr.audio.audiopop.popTest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.adulgr.audio.audiopop.R;

public class TestFragment extends Fragment {

  public TestFragment() {
    // Left blank intentionally
  }

  private Spinner sp;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.test_fragment, container, false);
    setSpinnerContent(view);
    return view;

  }


  private void setSpinnerContent(View view) {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.test_array,
        android.R.layout.simple_spinner_item);
    sp = (Spinner) view.findViewById(R.id.test_spinner);
    sp.setAdapter(adapter);

    sp.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
          int position, long id) {

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
      }
    });
  }



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("Test");
  }

}