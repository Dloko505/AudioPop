package com.adulgr.audio.audiopop.fragments.test;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.adulgr.audio.audiopop.R;
import java.util.Date;

@Entity
public class TestFragment extends Fragment {

  @PrimaryKey(autoGenerate = true)
  private long testId;
  @NonNull
  private Date timestamp;
  @NonNull
  private boolean testResults;

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
    sp = view.findViewById(R.id.test_spinner);
    sp.setAdapter(adapter);

    sp.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
          int position, long id) {

        Fragment fragment = null;

        switch ((String)parent.getSelectedItem()) {
          case "Mono":
            fragment = new TestMono();
            break;
          case "Stereo 2 ch":
            fragment = new TestStereo2();
            break;
          case "Stereo 4 ch":
            fragment = new TestStereo4();
            break;
        }


          //replacing the fragment
          if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.test_frame, fragment);
            ft.commit();
          }
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
